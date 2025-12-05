package com.routy.routyback.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routy.routyback.common.ApiResponse;
import com.routy.routyback.common.ParamProcessor;
import com.routy.routyback.domain.ProductImageVO;
import com.routy.routyback.dto.ProductUserDTO;
import com.routy.routyback.mapper.user.ProductUserMapper;

@Service
public class ProductUserService implements IProductUserService {

    @Autowired
    ProductUserMapper productUserMapper;


    @Override
    public ProductUserDTO productDetailView(int prdNo) { //사용자 제품 상세조회
        //기본 정보 조회
        ProductUserDTO product = productUserMapper.productDetailView(prdNo);
        //상품 없으면 null
        if (product == null) return null;
        //이미지 리스트 조회
        List<ProductImageVO> imgList = productUserMapper.selectProductImageList(prdNo);

        //이미지 분류 -제품 이미지, 설명용 이미지
        List<String> galleryList = new ArrayList<>();
        List<String> detailList = new ArrayList<>();

        //이미지 for문으로 나누기
        for (ProductImageVO img : imgList) {
            String type = img.getPiType();//일단 변수로

            if (type != null) { //null 체크하고, 공백( ) 제거
                type = type.trim();

                // GALLERY는 상품 이미지, DETAIL은 상품 설명용 이미지, 대소문자 상관없게 수정
                if ("GALLERY".equalsIgnoreCase(type)) {
                    galleryList.add(img.getPiUrl());
                } else if ("DETAIL".equalsIgnoreCase(type)) {
                    detailList.add(img.getPiUrl());
                }
            }
        }

        //만약 갤러리에 이미지 없으면 상품 테이블에 있는 이미지 보여주기
        if (galleryList.isEmpty() && product.getPrdImg() != null) {
            galleryList.add(product.getPrdImg());
        }

        //Map 생성해서 dto에 넣기
        Map<String, List<String>> imageMap = new HashMap<>();
        imageMap.put("gallery", galleryList);
        imageMap.put("detail", detailList);

        product.setImages(imageMap);

        return product;
    }

    @Override
    public ApiResponse getProductList(Map<String, Object> params) {
        List<ProductUserDTO> list = productUserMapper.searchProducts(params);
        return ApiResponse.success(list);
    }


    @Override
    public ApiResponse productAllSkinCate(Map<String, Object> params) { // 피부타입별 추천 제품목록
        try {
            params.put("limit", ParamProcessor.parseInt(params.get("limit"), 1));

            return ApiResponse.success(productUserMapper.productAllSkinCate(params));
        } catch (Exception e) {
            return ApiResponse.fromException(e);
        }
    }

    @Override
    public ApiResponse productPopular(Map<String, Object> params) {
        try {
            params.put("limit", ParamProcessor.parseInt(params.get("limit"), 4));
            params.remove("skin");
            params.remove("sub_cate");

            List<ProductUserDTO> result = productUserMapper.productAllSkinCate(params);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.fromException(e);
        }
    }


    @Override
    public ApiResponse productAllSkinCommend(Map<String, Object> params) { // 당신을 위한 맞춤 추천
        try {
            Object userNo = params.get("user_no");
            List<Integer> cateList = Arrays.asList(11001, 13001, 11003, 11005, 13005);

            // 카테고리값 받기
            if (userNo != null) {
                List<Integer> selectCateList = productUserMapper.productCateViewed(params);
                if (selectCateList.size() == 5) cateList = selectCateList;
            }

            params.put("limit", ParamProcessor.parseInt(params.get("limit"), 1));
            params.remove("skin");
            List<ProductUserDTO> resultData = new ArrayList<>();

            for (Integer cate : cateList) {
                params.put("sub_cate", cate);
                List<ProductUserDTO> tempData = productUserMapper.productAllSkinCate(params);

                resultData.addAll(tempData);
            }

            return ApiResponse.success(resultData);
        } catch (Exception e) {
            return ApiResponse.fromException(e);
        }
    }
    @Override
    public ApiResponse productSkinBased(Map<String, Object> params) {
        try {
            params.put("limit", ParamProcessor.parseInt(params.get("limit"), 4));

            // skin 없으면 추천 불가
            Object skin = params.get("skin");
            if (skin == null || skin.toString().isEmpty()) {
                return ApiResponse.success(new ArrayList<>());
            }

            // 피부타입 기반 인기순
            List<ProductUserDTO> list = productUserMapper.productAllSkinCate(params);
            return ApiResponse.success(list);

        } catch (Exception e) {
            return ApiResponse.fromException(e);
        }
    }
    @Override
    public ApiResponse getFallbackProducts(int limit) {
        List<ProductUserDTO> list = productUserMapper.selectFallbackProducts(limit);
        return ApiResponse.success(list);
    }
    @Override
    public ApiResponse filterProducts(Map<String, Object> param) {
        try {
            List<ProductUserDTO> list = productUserMapper.filterProducts(param);
            return ApiResponse.success(list);
        } catch (Exception e) {
            return ApiResponse.fromException(e);
        }
    }



}
