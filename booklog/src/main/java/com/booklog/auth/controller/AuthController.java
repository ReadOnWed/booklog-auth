package com.booklog.auth.controller;

import com.booklog.auth.oauth.google.GoogleService;
import com.booklog.auth.oauth.google.GoogleTokenDto;
import com.booklog.auth.oauth.google.GoogleUserDto;
import com.booklog.auth.oauth.kakao.KakaoService;
import com.booklog.auth.oauth.kakao.KakaoTokenDto;
import com.booklog.auth.oauth.kakao.KakaoUserDto;
import com.booklog.member.dto.MemberLoginDto;
import com.booklog.member.entity.Member;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class AuthController {

    private final KakaoService kakaoService;
    private final GoogleService googleService;

    @GetMapping("/kakaoLogin")
    public ResponseEntity<MemberLoginDto> kakaoLogin(@RequestParam("code") String code)
        throws URISyntaxException {
        try {
            KakaoTokenDto kakaoTokenDto = kakaoService.getKakaoAccessToken(code);
            KakaoUserDto kakaoUserDto = kakaoService.getKakaoUser(kakaoTokenDto.getAccessToken());
            Member loginMember = kakaoService.loginKakao(kakaoUserDto);
            URI redirectUri = new URI("http://localhost:3000");

            // ResponseEntity를 사용하여 리다이렉트를 반환
            return ResponseEntity.status(HttpStatus.FOUND)
                .location(redirectUri)
                .body(kakaoService.getMemberLoginDto(loginMember));
        } catch (Exception e) {
            URI errorRedirectUri = new URI("http://localhost:3000");

            // ResponseEntity를 사용하여 에러 상태와 리다이렉트를 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .location(errorRedirectUri)
                .build();
        }
    }

    @GetMapping("/googleLogin")
    public ResponseEntity<MemberLoginDto> googleLogin(@RequestParam("code") String code)
        throws URISyntaxException {
        try{
            GoogleTokenDto googleTokenDto = googleService.getGoogleAccessToken(code);
            GoogleUserDto googleUserDto = googleService.getGoogleUser(googleTokenDto.getAccessToken());
            Member loginMember = googleService.loginGoogle(googleUserDto);
            URI redirectUri = new URI("http://localhost:3000");

            // ResponseEntity를 사용하여 리다이렉트를 반환
            return ResponseEntity.status(HttpStatus.FOUND)
                .location(redirectUri)
                .body(kakaoService.getMemberLoginDto(loginMember));
        } catch (Exception e) {
            URI errorRedirectUri = new URI("http://localhost:3000");

            // ResponseEntity를 사용하여 에러 상태와 리다이렉트를 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .location(errorRedirectUri)
                .build();
        }
    }
}
