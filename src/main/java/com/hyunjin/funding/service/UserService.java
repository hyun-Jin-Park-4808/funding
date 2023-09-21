package com.hyunjin.funding.service;

import com.hyunjin.funding.domain.User;
import com.hyunjin.funding.dto.Auth;
import com.hyunjin.funding.dto.Auth.SighUp;
import com.hyunjin.funding.exception.impl.AlreadyExistUserException;
import com.hyunjin.funding.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    return this.userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID 입니다. -> " + loginId));
  }

  public User register(Auth.SighUp user) { // 회원 가입 메서드
    boolean exists = this.userRepository.existsByLoginId(user.getLoginId());
    if(exists) { // 아이디 중복 검사
      throw new AlreadyExistUserException();
    }
    user.setPassword(this.passwordEncoder.encode(user.getPassword())); // 비밀번호 인코딩해서 저장
    var result = this.userRepository.save(user.toEntity()); // 컴파일러가 변수타입 추정(var)
    return result;

  }

  public User authenticate(Auth.SighIn user) { // login 시 아이디, 비번 검증하기 위한 메서드
    var user1 = this.userRepository.findByLoginId(user.getLoginId())
        .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));

    if (this.passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
      // user.getPassword(raw 패스워드)와 user1.getPassword(인코딩된 패스워드)가 일치하는지 확인
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }
    return user1;
  }
}
