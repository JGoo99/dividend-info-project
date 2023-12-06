package com.example.dividend.model;

import com.example.dividend.persist.entity.MemberEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

public class Auth {

  @Data
  @Schema(description = "회원 가입 입력 정보")
  public static class signIn {
    @Schema(description = "회원명", example = "goo")
    private String username;
    @Schema(description = "회원 비밀번호", example = "goo123")
    private String password;
  }

  @Data
  @Schema(description = "회원 로그인 입력 정보")
  public static class signUp {
    @Schema(description = "회원명", example = "goo")
    private String username;
    @Schema(description = "회원 비밀번호", example = "goo123")
    private String password;
    @Schema(description = "회원 권한", example = "ROLE_READ")
    private List<String> roles;

    public MemberEntity toEntity() {
      return MemberEntity.builder()
        .username(this.username)
        .password(this.password)
        .roles(this.roles)
        .build();
    }
  }
}
