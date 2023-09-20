package com.hyunjin.funding.service;

import com.hyunjin.funding.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    return this.userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. -> " + loginId));
  }

  public void register() { // 회원 가입 메서드

  }

  public void authenticate() { // login 시 아이디, 비번 검증하기 위한 메서드

  }

}
