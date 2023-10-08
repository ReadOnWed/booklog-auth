package com.booklog.auth.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoTokenDto {

    @NotNull(message = "accessToken may not be null")
    @JsonProperty("access_token")
    private String accessToken;

    @NotNull(message = "refreshToken may not be null")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Builder
    public KakaoTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}