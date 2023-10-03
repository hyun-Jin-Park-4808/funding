package com.hyunjin.funding.controller;

import com.hyunjin.funding.Security.TokenProvider;
import com.hyunjin.funding.dto.Auth;
import com.hyunjin.funding.dto.MakerInput;
import com.hyunjin.funding.service.UserService;
import java.security.Principal;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final TokenProvider tokenProvider;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody Auth.SighUp request) {
    var result = this.userService.register(request);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Auth.SighIn request) {
    var user = this.userService.authenticate(request);
    var token = this.tokenProvider.generateToken(user.getLoginId(), user.getRoles());
    log.info("user login : " + request.getLoginId());
    return ResponseEntity.ok(token);
  }

  @PostMapping("/maker/business")
  @PreAuthorize("hasRole('SUPPORTER')") // 헤더에 정보 등록
  public ResponseEntity<?> signUpMakerBusiness(Principal principal, @RequestBody MakerInput makerInput) {

    String loginId = principal.getName();
    // 메이커 권한 추가
    this.userService.registerMakerAuthority(loginId);

    // 메이커 테이블에 데이터 추가
    var result = this.userService.registerMakerByBRM(loginId, makerInput.getCompanyName(),
        makerInput.getBusinessRegistrationNumber());
    return ResponseEntity.ok(result);
  }

  @PostMapping("/maker/phone")
  @PreAuthorize("hasRole('SUPPORTER')") // 헤더에 정보 등록
  public ResponseEntity<?> signUpMakerPhone(Principal principal, @RequestBody MakerInput makerInput) {

    String loginId = principal.getName();
    // 메이커 권한 추가
    this.userService.registerMakerAuthority(loginId);

    // 메이커 테이블에 데이터 추가
    var result = this.userService.registerMakerByPhone(loginId, makerInput.getCompanyName(),
        makerInput.getPhone());
    return ResponseEntity.ok(result);
  }
}
