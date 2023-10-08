package com.booklog.member.entity;

import com.booklog.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String profileImage;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String socialId;

    @Builder
    public Member(String name, String email, String profileImage, SocialType socialType,
        String socialId) {
        this.name = name == null ? "이름" : name;
        this.email = email == null ? "이메일" : email;
        this.profileImage = profileImage == null ? "" : profileImage;
        this.socialType = socialType;
        this.role = Role.USER;
        this.socialId = socialId;
    }

    public void update(String name) {
        this.name = name == null ? this.name : name;
    }
}
