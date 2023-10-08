package com.booklog.auth.oauth.kakao;

import com.booklog.auth.jwt.Mapper.JwtMapper;
import com.booklog.auth.jwt.service.JwtService;
import com.booklog.member.dto.MemberDto;
import com.booklog.member.dto.MemberLoginDto;
import com.booklog.member.entity.Member;
import com.booklog.member.entity.SocialType;
import com.booklog.member.mapper.MemberMapper;
import com.booklog.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;


    @Value("${KAKAO_CLIENT_ID}")
    private String KAKAO_CLIENT_ID;
    @Value("${KAKAO_CLIENT_SECRET}")
    private String KAKAO_CLIENT_SECRET;
    @Value("${KAKAO_REDIRECT_URI}")
    private String KAKAO_REDIRECT_URL;

    private final WebClient webClient;

    public KakaoTokenDto getKakaoAccessToken(String code) {
        String getTokenURL =
            "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id="
                + KAKAO_CLIENT_ID + "&client_secret=" + KAKAO_CLIENT_SECRET + "&redirect_uri="
                + KAKAO_REDIRECT_URL + "&code="
                + code;
        return webClient.post()
            .uri(getTokenURL)
            .retrieve()
            .bodyToMono(KakaoTokenDto.class).block();
    }

    public KakaoUserDto getKakaoUser(String kakaoAccessToken) {
        String getUserURL = "https://kapi.kakao.com/v2/user/me";

        return webClient.post()
            .uri(getUserURL)
            .header("Authorization", "Bearer " + kakaoAccessToken)
            .retrieve()
            .bodyToMono(KakaoUserDto.class)
            .block();
    }

    // TODO
    //  1. Google 로그인 사용 시 AuthService에 합치기
    //  2. JWT 사용할 때 Member 대신 Token Return
    @Transactional
    public Member loginKakao(KakaoUserDto kakaoUserDto) {
        log.info(kakaoUserDto.getAuthenticationCode(), "회원 카카오 로그인");

        Optional<Member> member = memberRepository.findBySocialIdAndDeletedFalse(
            kakaoUserDto.getAuthenticationCode());

        if (member.isPresent()) {
            log.info("회원가입 된 멤버입니다.");
            // TODO
            //  JWT Return Code Need
            return member.orElseThrow();
        }
        if (kakaoUserDto.getProperties() != null) {
            return memberRepository.save(Member.builder()
                .name(kakaoUserDto.getProperties().getName())
                .email(kakaoUserDto.getKakaoAccount().getEmail())
                .profileImage(kakaoUserDto.getProperties().getProfileImage())
                .socialType(SocialType.KAKAO)
                .socialId(kakaoUserDto.getAuthenticationCode())
                .build());
        }
        return memberRepository.save(Member.builder()
            .name(null)
            .email(null)
            .profileImage(null)
            .socialType(SocialType.KAKAO)
            .socialId(kakaoUserDto.getAuthenticationCode())
            .build());
    }

    public MemberLoginDto getMemberLoginDto(Member member) {
        MemberDto memberDto = MemberMapper.INSTANCE.entityToMemberDto(member);
        return MemberLoginDto.builder().memberDto(memberDto)
            .accessToken(
                jwtService.createAccessToken(JwtMapper.INSTANCE.MemberDtoToJwtDto(memberDto)))
            .build();
    }
}
