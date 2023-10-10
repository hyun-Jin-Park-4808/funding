package com.hyunjin.funding.controller;

import com.hyunjin.funding.domain.Follow;
import com.hyunjin.funding.domain.Wish;
import com.hyunjin.funding.service.UserService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/wish/{product_id}") // 찜하기 기능
  @PreAuthorize("hasRole('SUPPORTER')")
  public ResponseEntity<Wish> wish(Principal principal,
      @PathVariable(value = "product_id") long productId) {

    String loginId = principal.getName();

    var result = userService.wish(loginId, productId);

    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/wish/{product_id}") // 찜하기 취소 기능
  @PreAuthorize("hasRole('SUPPORTER')")
  public void deleteWish(Principal principal,
      @PathVariable(value = "product_id") long productId) {
    String loginId = principal.getName();

    userService.deleteWish(loginId, productId);
  }

  @PostMapping("/follow/{maker_id}") // 팔로우 기능
  @PreAuthorize("hasRole('SUPPORTER')")
  public ResponseEntity<Follow> follow(Principal principal,
      @PathVariable(value = "maker_id") long makerId) {

    String loginId = principal.getName();

    var result = userService.follow(loginId, makerId);

    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/follow/{maker_id}") // 팔로우 취소 기능
  @PreAuthorize("hasRole('SUPPORTER')")
  public void deleteFollow(Principal principal,
      @PathVariable(value = "maker_id") long makerId) {
    String loginId = principal.getName();

    userService.deleteFollow(loginId, makerId);
  }
}
