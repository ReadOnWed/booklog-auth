package com.booklog.member.controller;

import com.booklog.member.dto.MemberDto;
import com.booklog.member.dto.MemberUpdateDto;
import com.booklog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/memberInfo")
// TODO 이미 gateway에서 헤더에 고유식별자를 붙여서 넘기므로 해당 로직을 적용할 것, gateway cors문제 확인해볼 것
public class MemberController {

    private final MemberService memberService;

    @GetMapping({"", "/{memberId}"})
    public ResponseEntity<MemberDto> findMember(@PathVariable(required = false) Long memberId) {
        return new ResponseEntity<>(memberService.findMember(memberId), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<MemberDto> updateMember(@ModelAttribute MemberUpdateDto memberUpdateDto, Authentication authentication) {
        return new ResponseEntity<>(memberService.updateMember(memberUpdateDto, Long.parseLong(
            authentication.getName())), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(Authentication authentication) {
        memberService.deleteMember(Long.parseLong(
            authentication.getName()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
