package com.example.dividend.controller;

import com.example.dividend.model.Auth;
import com.example.dividend.security.TokenProvider;
import com.example.dividend.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Member", description = "Member API")
public class AuthController {

  private final MemberService memberService;
  private final TokenProvider tokenProvider;

  @PostMapping("/signup")
  @Operation(summary = "sing up member", description = "신규 회원 가입")
  public ResponseEntity<?> singUp(
    @Schema(name = "회원가입 입력 정보", implementation = Auth.signUp.class, example = "아이디, 비밀번호")
    @RequestBody Auth.signUp request) {
    var member = this.memberService.register(request);
    return ResponseEntity.ok(member);
  }

  @PostMapping("/signin")
  @Operation(summary = "sign in member", description = "기존 회원 로그인")
  public ResponseEntity<?> signIn(
    @Schema(name = "로그인 입력 정보", implementation = Auth.signIn.class, example = "아이디, 비밀번호, 권한")
    @RequestBody Auth.signIn request) {
    var member = this.memberService.authenticate(request);
    var token =
      this.tokenProvider.generateToken(request.getUsername(), member.getRoles());
    return ResponseEntity.ok(token);
  }
}
