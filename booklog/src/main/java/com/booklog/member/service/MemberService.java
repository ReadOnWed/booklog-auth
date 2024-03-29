package com.booklog.member.service;

import com.booklog.global.exception.exceptions.BadRequestException;
import com.booklog.member.dto.MemberDto;
import com.booklog.member.dto.MemberUpdateDto;
import com.booklog.member.entity.Member;
import com.booklog.member.mapper.MemberMapper;
import com.booklog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto findMember(Long findMemberId, Long myMemberId) {

        // 내 정보 조회
        if (findMemberId == null) {
            return MemberMapper.INSTANCE.entityToMemberDto(findMemberById(myMemberId));
        }
        // 타인 정보 조회
        // 추후에 프로필 공개 여부 추가할 수 있어 분리
        return MemberMapper.INSTANCE.entityToMemberDto(findMemberById(findMemberId));
    }

    @Transactional
    public MemberDto updateMember(MemberUpdateDto memberUpdateDto, Long memberId) {
        Member member = findMemberById(memberId);
        member.update(memberUpdateDto.getName());
        return MemberMapper.INSTANCE.entityToMemberDto(memberRepository.save(member));
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = findMemberById(memberId);
        member.delete();
        memberRepository.save(member);
    }

    public Member findMemberById(Long userId) {
        return memberRepository.findByIdAndDeletedFalse(userId)
            .orElseThrow(() -> new BadRequestException("해당하는 회원이 없습니다."));
    }
}
