package com.routy.routyback.service.user;        // 서비스 클래스가 속한 패키지 경로

import com.routy.routyback.mapper.user.MyUsedProductMapper; // MY_USED_PRODUCT 테이블에 접근하는 MyBatis 매퍼
import com.routy.routyback.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;                      // 생성자 주입을 자동으로 생성해주는 Lombok 어노테이션
import org.springframework.stereotype.Service;              // 서비스 클래스로 스프링 컨테이너에 등록하기 위한 어노테이션

import java.util.List;                                      // List 타입 사용을 위한 import

/**
 * MyUsedProductService
 *
 * - 사용자가 '현재 사용 중'으로 체크한 제품들을 관리하는 서비스 클래스
 * - Controller 에서는 userId(String)를 받고
 * - 여기에서 userId → userNo(Long) 으로 변환 후 Mapper 에 넘겨준다.
 */
@Service                                                   // 스프링이 관리하는 Service 빈으로 등록
@RequiredArgsConstructor                                   // final 필드를 매개변수로 받는 생성자를 Lombok이 자동 생성
public class MyUsedProductService {                        // 현재 사용 중 제품 관련 비즈니스 로직을 처리하는 클래스

    private final UserMapper userMapper;    // userId → userNo 변환을 직접 처리
    private final MyUsedProductMapper myUsedProductMapper; // MY_USED_PRODUCT 테이블에 접근하는 MyBatis Mapper 의존성

    /**
     * 사용자가 현재 사용 중으로 체크한 제품들의 PRDNO 리스트를 조회하는 메서드
     *
     * @param userId  로그인 아이디(문자열, PathVariable 로 받은 값)
     * @return 사용 중인 제품 번호(PRDNO)의 리스트
     */
    public List<Long> getUsedProducts(String userId) {     // userId 기반으로 사용 중 제품 번호 목록을 조회
        Long userNo = userMapper.findUserNoByUserId(userId);   // userId를 바로 userNo로 변환
        return myUsedProductMapper.selectUsedProducts(userNo); // 2) Mapper 를 통해 MY_USED_PRODUCT 에서 PRDNO 리스트 조회
    }

    /**
     * 특정 제품을 '현재 사용 중'으로 체크하는 메서드
     *
     * @param userId 로그인 아이디(문자열)
     * @param prdNo  사용 중으로 체크할 제품 번호(PRDNO)
     */
    public void addUsedProduct(String userId, Long prdNo) { // 사용자가 특정 제품을 사용 중으로 설정할 때 호출
        Long userNo = userMapper.findUserNoByUserId(userId);
        myUsedProductMapper.insertUsedProduct(userNo, prdNo); // 2) MY_USED_PRODUCT 테이블에 (USERNO, PRDNO) INSERT
    }

    /**
     * 특정 제품을 '현재 사용 중' 목록에서 해제하는 메서드
     *
     * @param userId 로그인 아이디(문자열)
     * @param prdNo  사용 중 해제할 제품 번호(PRDNO)
     */
    public void removeUsedProduct(String userId, Long prdNo) { // 사용자가 특정 제품을 사용 중에서 해제할 때 호출
        Long userNo = userMapper.findUserNoByUserId(userId);
        myUsedProductMapper.deleteUsedProduct(userNo, prdNo);   // 2) MY_USED_PRODUCT 테이블에서 해당 (USERNO, PRDNO) 삭제
    }
}
