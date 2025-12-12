package com.routy.routyback.service.user;

import com.routy.routyback.dto.user.myrouty.RoutineMonthlyItem;
import com.routy.routyback.dto.user.myrouty.RoutineRequest;
import com.routy.routyback.dto.user.myrouty.RoutineDetailResponse;
import com.routy.routyback.dto.user.myrouty.UsedProductDetail;
import java.util.Arrays;
import com.routy.routyback.mapper.user.RoutineMapper;
import com.routy.routyback.mapper.user.UserMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * RoutineService
 *
 * - ROUTINE 및 ROUTINE_USED_PRODUCT 데이터를 저장/조회하는 서비스
 * - Controller는 userId(String)를 사용하고, 여기서 userNo(Long) 변환 후 DB 작업을 수행한다.
 */
@Service
@RequiredArgsConstructor
public class RoutineService {

    private final UserMapper userMapper;        // userId → userNo 변환
    private final RoutineMapper routineMapper;  // 루틴 DB 접근

    /**
     * 월간 루틴 조회
     *
     * @param userId 문자열 사용자 아이디
     * @param year 조회할 연도
     * @param month 조회할 월
     * @return 월간 루틴 리스트
     */
    public List<RoutineMonthlyItem> getMonthlyRoutine(String userId, int year, int month) {
        Long userNo = userMapper.findUserNoByUserId(userId);          // userId → userNo 변환
        List<Map<String, Object>> rows = routineMapper
            .selectMonthlyRoutine(userNo, year, month);               // DB에서 Map 리스트로 조회

        List<RoutineMonthlyItem> result = new ArrayList<>();          // 최종 반환용 DTO 리스트

        for (Map<String, Object> row : rows) {
            RoutineMonthlyItem item = new RoutineMonthlyItem();       // 한 날짜에 대한 DTO 생성

            // ROUTINE_ID → Long
            Object idObj = row.get("ROUTINE_ID");
            if (idObj instanceof Number) {
                item.setRoutineId(((Number) idObj).longValue());
            }

            // ROUTINE_DATE → yyyy-MM-dd 문자열로 변환
            Object dateObj = row.get("ROUTINE_DATE");
            String dateStr = null;

            if (dateObj instanceof Date) {                            // java.sql.Date 인 경우
                LocalDate localDate = ((Date) dateObj).toLocalDate();
                dateStr = localDate.toString();                       // yyyy-MM-dd
            } else if (dateObj instanceof LocalDate) {                // 이미 LocalDate인 경우
                dateStr = ((LocalDate) dateObj).toString();
            } else if (dateObj != null) {                             // 혹시 모를 기타 타입 방어
                dateStr = dateObj.toString();
            }
            item.setDate(dateStr);

            // SUMMARY → String
            item.setSummary((String) row.get("SUMMARY"));

            result.add(item);                                         // 리스트에 추가
        }

        return result;
    }

    /**
     * 특정 날짜 루틴 상세 조회
     *
     * @param userId 문자열 사용자 아이디
     * @param date yyyy-MM-dd
     * @return 루틴 상세 정보
     */
    public RoutineDetailResponse getRoutineByDate(String userId, String date) {
        Long userNo = userMapper.findUserNoByUserId(userId);

        // ROUTINE 1건 조회
        Map<String, Object> row = routineMapper.selectRoutineByDate(userNo, date);
        if (row == null) {
            return null;
        }

        RoutineDetailResponse dto = new RoutineDetailResponse();

        // ROUTINE_ID → Long
        Object idObj = row.get("ROUTINE_ID");
        if (idObj instanceof Number) {
            dto.setRoutineId(((Number) idObj).longValue());
        }

        // DATE (ROUTINE_DATE)
        Object dateObj = row.get("ROUTINE_DATE");
        String dateStr = null;
        if (dateObj instanceof Date) {
            dateStr = ((Date) dateObj).toLocalDate().toString();
        } else if (dateObj instanceof LocalDate) {
            dateStr = ((LocalDate) dateObj).toString();
        } else if (dateObj != null) {
            dateStr = dateObj.toString();
        }
        dto.setDate(dateStr);

        // SUMMARY
        dto.setSummary((String) row.get("SUMMARY"));

        // EXTRA_ACTIVITIES (comma-separated → List)
        String extra = (String) row.get("EXTRA_ACTIVITIES");
        if (extra != null && !extra.isEmpty()) {
            dto.setExtraActivities(Arrays.asList(extra.split(",")));
        } else {
            dto.setExtraActivities(List.of());
        }

        // DAILY_REVIEW
        dto.setDailyReview((String) row.get("DAILY_REVIEW"));

        // 사용 제품 조회 (ROUTINE_ID 기준)
        Long routineId = dto.getRoutineId();
        List<Map<String, Object>> productRows = routineMapper.selectRoutineUsedProducts(routineId);

        List<UsedProductDetail> usedList = new ArrayList<>();

        for (Map<String, Object> p : productRows) {
            UsedProductDetail up = new UsedProductDetail();

            // 제품 번호
            Object prdObj = p.get("PRDNO");
            if (prdObj instanceof Number) {
                up.setPrdNo(((Number) prdObj).longValue());
            }

            // 제품명 / 이미지
            up.setPrdName((String) p.get("PRD_NAME"));
            up.setPrdImg((String) p.get("PRD_IMG"));

            // 반응
            up.setReaction((String) p.get("REACTION_TYPE"));

            // 메모
            up.setMemo((String) p.get("MEMO"));

            // 알림 날짜
            Object alertObj = p.get("REUSE_ALERT_DATE");
            up.setAlertDate(alertObj != null ? alertObj.toString() : null);

            usedList.add(up);
        }

        dto.setProducts(usedList);

        return dto;
    }

    /**
     * 특정 날짜 루틴 저장(INSERT or UPDATE)
     *
     * @param userId 문자열 사용자 아이디
     * @param date 날짜 문자열 (yyyy-MM-dd)
     * @param request 루틴 저장 요청 DTO
     */
    public void saveRoutine(String userId, String date, RoutineRequest request) {

        Long userNo = userMapper.findUserNoByUserId(userId);

        // 1) 루틴 존재 여부 확인
        boolean exists = routineMapper.existsRoutine(userNo, date) > 0;

        String extraActivities = (request.getExtraActivities() != null)
            ? String.join(",", request.getExtraActivities())
            : null;

        if (exists) {
            routineMapper.updateRoutine(
                userNo,
                date,
                request.getSummary(),
                extraActivities,
                request.getDailyReview()
            );
        } else {
            routineMapper.insertRoutine(
                userNo,
                date,
                request.getSummary(),
                extraActivities,
                request.getDailyReview()
            );
        }

        // 3) ROUTINE_ID 다시 조회
        Long routineId = routineMapper.findRoutineId(userNo, date);

        // 4) 기존 사용 제품 전체 삭제
        routineMapper.deleteRoutineUsedProducts(userNo, date);

        // 5) 새 사용 제품 insert
        if (request.getProducts() != null) {
            for (RoutineRequest.UsedProduct p : request.getProducts()) {

                // prdNo가 없거나 0 이하이면 저장하지 않음
                if (p.getPrdNo() == null || p.getPrdNo() <= 0) {
                    continue;
                }

                // alertDate가 빈 문자열이면 null로 변환 (Oracle DATE 대응)
                if (p.getAlertDate() != null && p.getAlertDate().isBlank()) {
                    p.setAlertDate(null);
                }

                routineMapper.insertRoutineUsedProduct(
                    routineId,
                    p.getPrdNo(),
                    p.getReaction(),
                    p.getMemo(),
                    p.getAlertDate()   // yyyy-MM-dd 또는 null
                );
            }
        }
    }

    /**
     * 특정 날짜 루틴 삭제
     *
     * @param userId 문자열 사용자 아이디
     * @param date yyyy-MM-dd
     */
    public void deleteRoutine(String userId, String date) {

        // userId → userNo 변환
        Long userNo = userMapper.findUserNoByUserId(userId);

        // 해당 날짜 루틴 ID 조회
        Long routineId = routineMapper.findRoutineId(userNo, date);
        if (routineId == null) {
            // 이미 없으면 아무 작업도 하지 않음 (멱등성 보장)
            return;
        }

        // 하위 테이블 먼저 삭제 (FK 보호)
        routineMapper.deleteRoutineUsedProducts(userNo, date);

        // 루틴 메인 삭제
        routineMapper.deleteRoutine(userNo, date);
    }
}
