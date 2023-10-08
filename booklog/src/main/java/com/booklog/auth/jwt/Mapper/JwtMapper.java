package com.booklog.auth.jwt.Mapper;

import com.booklog.auth.jwt.dto.JwtDto;
import com.booklog.member.dto.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JwtMapper {

    JwtMapper INSTANCE = Mappers.getMapper(JwtMapper.class);

    JwtDto MemberDtoToJwtDto(MemberDto memberDto);
}
