package com.hyunjin.funding.service;

import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.User;
import com.hyunjin.funding.dto.Auth;
import com.hyunjin.funding.exception.impl.AlreadyExistUserException;
import com.hyunjin.funding.repository.MakerRepository;
import com.hyunjin.funding.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
  private final MakerRepository makerRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    return this.userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID 입니다. -> " + loginId));
  }

  public User register(Auth.SighUp user) { // 회원 가입 메서드
    boolean exists = this.userRepository.existsByLoginId(user.getLoginId());

    if (exists) { // 아이디 중복 검사
      throw new AlreadyExistUserException();
    }

    user.setPassword(this.passwordEncoder.encode(user.getPassword())); // 비밀번호 인코딩해서 저장
    var result = this.userRepository.save(user.toEntity()); // 컴파일러가 변수타입 추정(var)
    return result;

  }

  public User authenticate(Auth.SighIn user) { // login 시 아이디, 비번 검증하기 위한 메서드
    var user1 = this.userRepository.findByLoginId(user.getLoginId())
        .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));

    if (!this.passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
      // user.getPassword(raw 패스워드)와 user1.getPassword(인코딩된 패스워드)가 일치하는지 확인
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }
    return user1;
  }


  public User registerMakerAuthority(String loginId) {
    var user1 = this.userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

    String[] rolesArr = {"ROLE_SUPPORTER", "ROLE_MAKER"};
    List<String> roles = new ArrayList<String>();
    roles.addAll(Arrays.asList(rolesArr));

    var result = this.userRepository.save(User.builder()
        .userId(user1.getUserId())
        .loginId(user1.getLoginId())
        .password(user1.getPassword())
        .roles(roles)
        .createdDate(user1.getCreatedDate())
        .modifiedDate(LocalDateTime.now())
        .build());
    return result;
  }

  public Maker registerMakerByBRM(String loginId, String businessRegistrationNumber) {
    boolean exists = this.makerRepository.existsByBusinessRegistrationNumber(
        businessRegistrationNumber);
    var user = this.userRepository.findByLoginId(loginId);
    long userId = user.get().getUserId();

    if (exists) { // 사업자등록번호 중복 검사
      throw new AlreadyExistUserException();
    }

    return makerRepository.save(Maker.builder()
        .businessRegistrationNumber(businessRegistrationNumber)
        .userId(userId)
        .build());
  }

  public Maker registerMakerByPhone(String loginId, String phone) {
    boolean exists = this.makerRepository.existsByPhone(
        phone);
    var user = this.userRepository.findByLoginId(loginId);
    long userId = user.get().getUserId();

    if (exists) { // 전화번호 중복 검사
      throw new AlreadyExistUserException();
    }

    return makerRepository.save(Maker.builder()
        .phone(phone)
        .userId(userId)
        .build());
  }
}
