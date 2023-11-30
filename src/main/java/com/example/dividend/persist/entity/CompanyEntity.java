package com.example.dividend.persist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "company")
@Getter
@ToString
@NoArgsConstructor
public class CompanyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long companyId;

  @Column(unique = true)
  private String ticker;

  private String name;
}
