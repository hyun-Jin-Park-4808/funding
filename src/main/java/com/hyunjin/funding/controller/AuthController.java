package com.hyunjin.funding.controller;

import com.hyunjin.funding.Security.TokenProvider;
import com.hyunjin.funding.dto.Auth;
import com.hyunjin.funding.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
}
