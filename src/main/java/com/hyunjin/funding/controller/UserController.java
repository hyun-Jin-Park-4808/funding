package com.hyunjin.funding.controller;

import com.hyunjin.funding.domain.Follow;
import com.hyunjin.funding.domain.Wish;
import com.hyunjin.funding.dto.user.FollowInput;
import com.hyunjin.funding.dto.user.WishInput;
import com.hyunjin.funding.service.UserService;
import io.swagger.annotations.ApiOperation;

import java.security.Principal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "원하는 상품을 찜하기 위한 api입니다.\n "
            + "토큰 정보가 필요합니다.")
    @PostMapping("/wish") // 찜하기 기능
    @PreAuthorize("hasRole('SUPPORTER')")
    public ResponseEntity<Wish> addWish(
            Principal principal, @RequestBody WishInput wishInput) {
        String loginId = principal.getName();
        var result = userService.wish(loginId, wishInput.getProductId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "찜하기 취소를 위한 api입니다.\n "
            + "토큰 정보가 필요합니다.")
    @DeleteMapping("/wish") // 찜하기 취소 기능
    @PreAuthorize("hasRole('SUPPORTER')")
    public void deleteWish(Principal principal, @RequestBody WishInput wishInput) {
        String loginId = principal.getName();
        userService.deleteWish(loginId, wishInput.getProductId());
    }

    @ApiOperation(value = "원하는 메이커를 팔로우하기 위한 api입니다.\n "
            + "토큰 정보가 필요합니다.")
    @PostMapping("/follow") // 팔로우 기능
    @PreAuthorize("hasRole('SUPPORTER')")
    public ResponseEntity<Follow> addFollow(Principal principal, @RequestBody FollowInput followInput) {
        String loginId = principal.getName();
        var result = userService.follow(loginId, followInput.getMakerId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "메이커 팔로우 취소를 위한 api입니다.\n "
            + "토큰 정보가 필요합니다.")
    @DeleteMapping("/follow") // 팔로우 취소 기능
    @PreAuthorize("hasRole('SUPPORTER')")
    public void deleteFollow(Principal principal, @RequestBody FollowInput followInput) {
        String loginId = principal.getName();
        userService.deleteFollow(loginId, followInput.getMakerId());
    }
}
