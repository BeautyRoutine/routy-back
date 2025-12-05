package com.routy.routyback.service.user;

import com.routy.routyback.dto.OrderSaveRequestDTO;
import com.routy.routyback.dto.PaymentConfirmRequestDTO;
import com.routy.routyback.dto.order.OrdersDTO;
import com.routy.routyback.mapper.user.PayMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PayMapper payMapper;

    @Value("${toss.secret-key}")
    private String secretKey;

    // 주문서(청구서) 생성
    @Transactional
    public Long createOrder(Long authenticatedUserNo, OrderSaveRequestDTO request) {

        // ORDERS DTO 생성
        OrdersDTO ordersDto = new OrdersDTO();
        ordersDto.setUserNo(authenticatedUserNo); // 유저 번호
        ordersDto.setOdName(request.getReceiverName());
        ordersDto.setOdHp(request.getReceiverPhone());
        ordersDto.setOdZip(request.getZipCode() != null ? request.getZipCode() : 0);
        ordersDto.setOdRoadAddr(request.getRoadAddress());
        ordersDto.setOdDetailAddr(request.getDetailAddress());
        ordersDto.setOdDelvMsg(request.getDeliveryMsg());
        ordersDto.setOdDelvKeyType(1);

        // 금액 저장
        long totalAmount = request.getTotalAmount() != null ? request.getTotalAmount() : 0L;
        long deliveryFee = request.getDeliveryFee() != null ? request.getDeliveryFee() : 0L;
        ordersDto.setOdPrdPrice((int) (totalAmount - deliveryFee));
        ordersDto.setOdDelvPrice((int) deliveryFee);

        // ORDERS 테이블 저장 -> odNo 생성
        payMapper.insertOrder(ordersDto);
        long generatedOdNo = ordersDto.getOdNo();

        // 상품 상세 저장 (PAY_PRODUCT_MAPPING)
        // 상품 정보는 미리 박제해놔야 "뭘 결제할 건지" 알 수 있음
        for (OrderSaveRequestDTO.OrderItemDto item : request.getItems()) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("odNo", generatedOdNo);
            itemMap.put("prdNo", item.getPrdNo());
            itemMap.put("qty", item.getQuantity());
            itemMap.put("price", item.getPrice());

            payMapper.insertPayProduct(itemMap);
        }

        // 토스 결제용 orderId로 사용할 odNo 리턴
        return ordersDto.getOdNo();
    }

    // 결제 승인 및 영수증(PAY) 발급
    @Transactional
    public void confirmPayment(PaymentConfirmRequestDTO request) {

        Long odNo = Long.parseLong(request.getOrderId());

        Integer payCount = payMapper.countPayByOdNo(odNo);
        if (payCount != null && payCount > 0) {
            return; // ← 중복 요청이면 그냥 성공으로 처리
        }

        // 금액 검증 (ORDERS 테이블에 적힌 금액과 비교)
        OrdersDTO orderInfo = payMapper.selectOrder(odNo);

        if (orderInfo == null) {
            throw new RuntimeException("주문 정보 없음");
        }

        // DB총액(상품+배송비) vs 결제요청금액 비교
        long dbTotalAmount = orderInfo.getOdPrdPrice() + orderInfo.getOdDelvPrice();

        if (dbTotalAmount != request.getAmount()) {
            throw new RuntimeException("결제 금액 불일치");
        }

        // 토스 승인 요청 (동일)
        String encodedKey = Base64.getEncoder()
            .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("paymentKey", request.getPaymentKey());
        body.put("orderId", request.getOrderId());
        body.put("amount", request.getAmount());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.postForEntity("https://api.tosspayments.com/v1/payments/confirm", entity,
                String.class);

            // 성공했으니 이제 PAY 테이블(영수증) 생성
            Map<String, Object> payMap = new HashMap<>();
            payMap.put("odNo", odNo);
            payMap.put("payPrice", request.getAmount());
            payMap.put("payType", 201); // 카드
            payMap.put("payResNo", request.getPaymentKey()); // 승인번호 바로 저장

            // insertPay 호출! (status는 1로 저장)
            payMapper.insertPaySuccess(payMap);

            // 결제 성공 후 해당 상품 삭제
            Map<String, Object> deleteMap = new HashMap<>();
            deleteMap.put("odNo", odNo);
            deleteMap.put("userNo", orderInfo.getUserNo());
            payMapper.deleteCartItemsByOrder(deleteMap);

        } catch (Exception e) {
            throw new RuntimeException("결제 승인 실패: " + e.getMessage());
        }
    }
}