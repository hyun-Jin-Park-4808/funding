package com.hyunjin.funding.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MAKER")
public class Maker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "maker_id")
  private Long makerId;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "business_registration_number")
  private String businessRegistrationNumber;

  @Column(name = "phone")
  private String phone;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @JsonIgnore
  @OneToMany(mappedBy = "maker", fetch = FetchType.LAZY)
  private List<Product> products = new ArrayList<>();
}
