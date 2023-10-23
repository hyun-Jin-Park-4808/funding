package com.hyunjin.funding.controller;

import com.hyunjin.funding.security.TokenProvider;
import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.User;
import com.hyunjin.funding.dto.SignIn;
import com.hyunjin.funding.dto.MakerInput;
import com.hyunjin.funding.dto.SignUp;
import com.hyunjin.funding.service.AuthService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
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

  private final AuthService authService;
  private final TokenProvider tokenProvider;

  @ApiOperation(value = "회원가입 api입니다.")
  @PostMapping("/signup")
  public ResponseEntity<User> signUp(@RequestBody SignUp request) {
    var result = this.authService.register(request);
    return ResponseEntity.ok(result);
  }

  @ApiOperation(value = "로그인 api입니다.",
      notes = "회원가입 이후 이용할 수 있습니다.\n "
          + "로그인 후 발급된 토큰을 복사하여 필요한 API 헤더에 붙여넣기 해주세요.")
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody SignIn request) {
    var user = this.authService.authenticate(request);
    var token = this.tokenProvider.generateToken(user.getLoginId(), user.getRoles());
    log.info("user login : " + request.getLoginId());
    return ResponseEntity.ok(token);
  }

  @ApiOperation(value = "법인 사업자 등록을 위한 api입니다.",
      notes = "로그인 이후 발급받은 토큰을 헤더에 Bearer +token 형태로 입력해야 이용할 수 있습니다.")
  @PostMapping("/maker/business")
  @PreAuthorize("hasRole('SUPPORTER')") // 헤더에 정보 등록
  public ResponseEntity<Maker> signUpMakerBusiness(Principal principal, @RequestBody MakerInput makerInput) {

    String loginId = principal.getName();
    // 메이커 권한 추가
    this.authService.registerMakerAuthority(loginId);

    // 메이커 테이블에 데이터 추가
    var result = this.authService.registerMakerByBRM(loginId, makerInput.getCompanyName(),
        makerInput.getBusinessRegistrationNumber());
    return ResponseEntity.ok(result);
  }

  @ApiOperation(value = "개인 사업자 등록을 위한 api입니다.",
      notes = "로그인 이후 발급받은 토큰을 헤더에 Bearer +token 형태로 입력해야 이용할 수 있습니다.")
  @PostMapping("/maker/phone")
  @PreAuthorize("hasRole('SUPPORTER')") // 헤더에 정보 등록
  public ResponseEntity<Maker> signUpMakerPhone(Principal principal, @RequestBody MakerInput makerInput) {

    String loginId = principal.getName();
    // 메이커 권한 추가
    this.authService.registerMakerAuthority(loginId);

    // 메이커 테이블에 데이터 추가
    var result = this.authService.registerMakerByPhone(loginId, makerInput.getCompanyName(),
        makerInput.getPhone());
    return ResponseEntity.ok(result);
  }
}
