package com.booklog.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberLoginDto {

    private MemberDto memberDto;
    private String accessToken;

    @Builder
    public MemberLoginDto(MemberDto memberDto, String accessToken) {
        this.memberDto = memberDto;
        this.accessToken=accessToken;
    }
}
