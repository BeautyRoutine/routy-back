package com.routy.routyback.controller.user;

import com.routy.routyback.dto.ProductUserDTO;
import com.routy.routyback.service.user.IProductUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // restapi
@RequestMapping("/api/products") // 기본 주소
public class ProductUserController {

    // DI 주입
    @Autowired
    @Qualifier("productUserService")
    IProductUserService service;  // 서비스 인터페이스 주입

    @GetMapping("/{prdNo}") //   /api/products/123 등
    public ProductUserDTO productDetailView(@PathVariable int prdNo) { // DTO 타입
        ProductUserDTO dto = service.productDetailView(prdNo);  // dto에 서비스 detailview로 객체 만들어서 저장

        return dto; // dto 반환(json)
    }
}
