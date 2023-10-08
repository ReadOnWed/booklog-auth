package com.booklog.auth.oauth.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OAuthReqDto {
    private String code;
}
