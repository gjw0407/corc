package com.web.shinhan.service.paymentItem.jwt;

import com.web.shinhan.model.AdminDto;
import com.web.shinhan.model.StoreDto;
import com.web.shinhan.model.UserDto;

import java.util.Map;

public interface JwtService {

    // 로그인 성공시 사용자 정보를 기반으로 JWTToken을 생성하여 반환.
    String create(UserDto userDto);

    // 로그인 성공시 사용자 정보를 기반으로 JWTToken을 생성하여 반환.
    String createAdmin(AdminDto adminDto);

    // 로그인 성공시 사용자 정보를 기반으로 JWTToken을 생성하여 반환.
    String createStore(StoreDto storeDto);

    // 전달 받은 토큰이 제대로 생성된것이니 확인 하고 문제가 있다면 RuntimeException을 발생.
    void checkValid(String jwt);

    // JWT Token을 분석해서 필요한 정보를 반환.
    Map<String, Object> get(String jwt);
}
