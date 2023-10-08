package com.booklog.auth.jwt.controller;


import com.booklog.auth.jwt.Mapper.JwtMapper;
import com.booklog.auth.jwt.service.JwtService;
import com.booklog.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("jwt")
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("/access-token-test")
    public String accessTokenTest(@RequestBody MemberDto memberDto) {
        System.out.println(memberDto.toString());
        return jwtService.createAccessToken(JwtMapper.INSTANCE.MemberDtoToJwtDto(memberDto));
    }
}
