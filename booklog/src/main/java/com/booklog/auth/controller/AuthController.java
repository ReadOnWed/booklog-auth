package com.booklog.auth.controller;

import com.booklog.auth.oauth.common.OAuthReqDto;
import com.booklog.auth.oauth.google.GoogleService;
import com.booklog.auth.oauth.google.GoogleTokenDto;
import com.booklog.auth.oauth.google.GoogleUserDto;
import com.booklog.auth.oauth.kakao.KakaoService;
import com.booklog.auth.oauth.kakao.KakaoTokenDto;
import com.booklog.auth.oauth.kakao.KakaoUserDto;
import com.booklog.member.dto.MemberLoginDto;
import com.booklog.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class AuthController {

    private final KakaoService kakaoService;
    private final GoogleService googleService;

    @PostMapping("/kakaoLogin")
    public ResponseEntity<MemberLoginDto> kakaoLogin(@RequestBody OAuthReqDto oAuthReqDto) {
        KakaoTokenDto kakaoTokenDto = kakaoService.getKakaoAccessToken(oAuthReqDto.getCode());
        KakaoUserDto kakaoUserDto = kakaoService.getKakaoUser(kakaoTokenDto.getAccessToken());
        Member loginMember = kakaoService.loginKakao(kakaoUserDto);
        return new ResponseEntity<>(kakaoService.getMemberLoginDto(loginMember), HttpStatus.OK);
    }

    @PostMapping("/googleLogin")
    public ResponseEntity<MemberLoginDto> googleLogin(@RequestBody OAuthReqDto oAuthReqDto) {
        GoogleTokenDto googleTokenDto = googleService.getGoogleAccessToken(oAuthReqDto.getCode());
        GoogleUserDto googleUserDto = googleService.getGoogleUser(googleTokenDto.getAccessToken());
        Member loginMember = googleService.loginGoogle(googleUserDto);
        return new ResponseEntity<>(kakaoService.getMemberLoginDto(loginMember), HttpStatus.OK);
    }
}
