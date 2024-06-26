package com.hyunjin.funding.service;

import com.hyunjin.funding.domain.Maker;
import com.hyunjin.funding.domain.User;
import com.hyunjin.funding.dto.auth.SignIn;
import com.hyunjin.funding.dto.auth.SignUp;
import com.hyunjin.funding.dto.type.Authority;
import com.hyunjin.funding.exception.impl.AlreadyExistUserException;
import com.hyunjin.funding.exception.impl.UserNotFoundException;
import com.hyunjin.funding.repository.MakerRepository;
import com.hyunjin.funding.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements UserDetailsService {

  private final UserRepository userRepository;
  private final MakerRepository makerRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    return this.userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID 입니다. -> " + loginId));
  }

  @Transactional
  public User register(SignUp user) { // 회원 가입 메서드
    List<String> rolesArr = new ArrayList<>();
    rolesArr.add(String.valueOf(Authority.ROLE_SUPPORTER));
    boolean exists = this.userRepository.existsByLoginId(user.getLoginId());

    if (exists) { // 아이디 중복 검사
      throw new AlreadyExistUserException();
    }

    user.setPassword(this.passwordEncoder.encode(user.getPassword())); // 비밀번호 인코딩해서 저장
      return this.userRepository.save(user.toEntity(rolesArr));

  }

  public User authenticate(SignIn user) { // login 시 아이디, 비번 검증하기 위한 메서드
    var user1 = this.userRepository.findByLoginId(user.getLoginId())
        .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다."));

    if (!this.passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
      // user.getPassword(raw 패스워드)와 user1.getPassword(인코딩된 패스워드)가 일치하는지 확인
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }
    return user1;
  }

  @Transactional
  public User registerMakerAuthority(String loginId) {
    var user1 = this.userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

    String[] rolesArr = {"ROLE_SUPPORTER", "ROLE_MAKER"};
      List<String> roles = new ArrayList<> (Arrays.asList(rolesArr));

      return this.userRepository.save(User.builder()
        .userId(user1.getUserId())
        .loginId(user1.getLoginId())
        .password(user1.getPassword())
        .roles(roles)
        .createdDate(user1.getCreatedDate())
        .modifiedDate(LocalDateTime.now())
        .build());
  }

  @Transactional
  public Maker registerMakerByBRM(String loginId, String companyName,
      String businessRegistrationNumber) {
    boolean exists = this.makerRepository.existsByBusinessRegistrationNumber(
        businessRegistrationNumber);
    var user = this.userRepository.findByLoginId(loginId)
            .orElseThrow(UserNotFoundException::new);

    if (exists) { // 사업자등록번호 중복 검사
      throw new AlreadyExistUserException();
    }

    return makerRepository.save(Maker.builder()
        .companyName(companyName)
        .businessRegistrationNumber(businessRegistrationNumber)
        .user(user)
        .build());
  }

  @Transactional
  public Maker registerMakerByPhone(String loginId, String companyName, String phone) {
    boolean exists = this.makerRepository.existsByPhone(
        phone);
    var user = this.userRepository.findByLoginId(loginId)
            .orElseThrow(UserNotFoundException::new);

    if (exists) { // 전화번호 중복 검사
      throw new AlreadyExistUserException();
    }

    return makerRepository.save(Maker.builder()
        .companyName(companyName)
        .phone(phone)
        .user(user)
        .build());
  }
}