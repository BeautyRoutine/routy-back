package com.routy.routyback.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.routy.routyback.mapper.admin.IOrdersAdmDAO;
import com.routy.routyback.mapper.admin.IOrderDetailAdmDAO;
import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.common.ParamProcessor;
import com.routy.routyback.common.category.CategoryRepository;
import com.routy.routyback.dto.DeliveryDTO;
import com.routy.routyback.dto.OrderClaimDTO;
import com.routy.routyback.dto.OrderPrdDTO;
import com.routy.routyback.dto.OrdersUsDTO;

@Service
public class OrderAdmService implements IOrderAdmService {
	@Autowired
	@Qualifier("IOrdersAdmDAO")
	IOrdersAdmDAO dao;
	
	@Autowired
	@Qualifier("IOrderDetailAdmDAO")
	IOrderDetailAdmDAO detdao;
	
	@Autowired
	CategoryRepository cateRepo;



	@Override
	public ApiResponse listAllOrders(Map<String, Object> params) {
		try {
			// param 재가공
			ParamProcessor.paging(params);
			ParamProcessor.likeBothString(params, "mem_name");
			
			int total = dao.listAllOrdersCount(params);

			ArrayList<OrdersUsDTO> resultList = dao.listAllOrders(params);
	        
	        Map<String, Object> result = new java.util.HashMap<>();
	        result.put("total", total);
	        result.put("list", resultList);
	        
	        return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	@Override
	public ApiResponse detailOrder(int odNo) {
		try {
			OrdersUsDTO resultRow = dao.detailOrder(odNo);
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}
	
	@Override
	public ApiResponse detailPrdOrder(int odNo) {
		try {
			ArrayList<OrderPrdDTO> resultRow = detdao.detailPrdOrder(odNo);
			
			for(OrderPrdDTO row : resultRow) {
				int mainNo = row.getPrdMainCate();
				int subNo = row.getPrdSubCate();
				row.setMainCateStr(cateRepo.getMainCateStr(mainNo));
				row.setSubCateStr(cateRepo.getSubCateStr(subNo));
			}
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}
	
	@Override
	public ApiResponse detailDelvOrder(int odNo) {
		try {
			ArrayList<DeliveryDTO> resultRow = detdao.detailDelvOrder(odNo);
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}



	@Override
	public ApiResponse listAllOrdersDelivery(Map<String, Object> params) {
		try {
			// param 재가공
			ParamProcessor.paging(params);
			ParamProcessor.likeBothString(params, "mem_name");
			
			int total = dao.listAllOrdersDeliveryCount(params);

			ArrayList<DeliveryDTO> resultList = dao.listAllOrdersDelivery(params);
	        
	        Map<String, Object> result = new java.util.HashMap<>();
	        result.put("total", total);
	        result.put("list", resultList);
	        
	        return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	@Override
	public ApiResponse detailOrderDelivery(int delvNo) {
		try {
			DeliveryDTO resultRow = dao.detailOrderDelivery(delvNo);
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	@Override
	public ApiResponse insertOrderDelivery(DeliveryDTO dto) {
		try {
			dao.insertOrderDelivery(dto);
			int delvNo = dto.getDelvNo();
			
			Map<String, Object> result = new java.util.HashMap<>();
	        result.put("delvNo", delvNo);
	        System.out.println("qnaNO="+dto.getQnaNo());
	        
	        // 환불&교환인 경우 요청&주문 업데이트
	        if(dto.getQnaNo() != null && dto.getQnaNo() > 0) {
	        	// 요청 업데이트
	        	dto.setQnaStatus(2);
	        	dto.setQnaA("반품 신청 완료되었습니다.");
	        	dao.updateOrderClaim(dto);
	        	// 주문 업데이트
	        	dto.setOdStatus(3);
	        	dao.updateOrder(dto);
	        }
			
			return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
		
	}

	@Override
	public ApiResponse updateOrderDelivery(DeliveryDTO dto) {
		try {
			dao.updateOrderDelivery(dto);
			int delvNo = dto.getDelvNo();
			
			if(dto.getDelvStatus() > 2) {
	        	// 주문 업데이트
				if(dto.getDelvStatus() == 3 || dto.getDelvStatus() == 4 || dto.getDelvStatus() == 5) dto.setOdStatus(4);
				if(dto.getDelvStatus() == 6) {
					dto.setOdStatus(5);
					// 배송완료 시 used_product에 추가
					OrdersUsDTO orderInfo = dao.detailOrder(dto.getOdNo());
					ArrayList<OrderPrdDTO> productList = detdao.detailPrdOrder(dto.getOdNo());
					for (OrderPrdDTO product : productList) {
						Map<String, Object> params = new HashMap<>();
						params.put("user_no", orderInfo.getUserNo());
						params.put("prd_no", product.getPrdNo());
						dao.insertUsedProduct(params);
					}
				}
				dao.updateOrder(dto);
				// 요청 업데이트
				if(dto.getDelvStatus() == 6) {
					System.out.println("qnaNo="+dto.getQnaNo());
					dto.setQnaStatus(3);
					System.out.println("qnaStatus="+dto.getQnaStatus());
					dao.updateOrderClaim(dto);
				}
	        }
			
			Map<String, Object> result = new java.util.HashMap<>();
	        result.put("msg", "delvNo:"+delvNo+" 택배를 수정하였습니다.");
			
			return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

	@Override
	public ApiResponse deleteOrderDelivery(int delvNo) {
		try {
			dao.deleteOrderDelivery(delvNo);
			
			Map<String, Object> result = new java.util.HashMap<>();
	        result.put("msg", "delvNo:"+delvNo+" 택배를 삭제하였습니다.");
			
			return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}



	@Override
	public ApiResponse listAllOrdersClaim(Map<String, Object> params) {
		try {
			// param 재가공
			ParamProcessor.paging(params);
			ParamProcessor.likeBothString(params, "m_name");
			
			int total = dao.listAllOrdersClaimCount(params);

			ArrayList<OrderClaimDTO> resultList = dao.listAllOrdersClaim(params);
	        
	        Map<String, Object> result = new java.util.HashMap<>();
	        result.put("total", total);
	        result.put("list", resultList);
	        
	        return ApiResponse.success(result);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}
	
	@Override
	public ApiResponse detailOrderClaim(int qnaNo) {
		try {
			OrderClaimDTO resultRow = dao.detailOrderClaim(qnaNo);
			
			return ApiResponse.success(resultRow);
		} catch (Exception e) {
			return ApiResponse.fromException(e);
		}
	}

}
