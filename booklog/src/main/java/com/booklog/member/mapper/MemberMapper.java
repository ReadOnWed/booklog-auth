package com.booklog.member.mapper;

import com.booklog.member.dto.MemberDto;
import com.booklog.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDto entityToMemberDto(Member member);
}
