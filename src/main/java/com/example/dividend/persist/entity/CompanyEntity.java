package com.example.dividend.persist.entity;

import com.example.dividend.model.Company;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "company")
@Getter
@ToString
@NoArgsConstructor
@Schema(description = "Company DB")
public class CompanyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "회사 식별 아이디", example = "1")
  private Long companyId;

  @Column(unique = true)
  @Schema(description = "회사 식별 코드", example = "COKE")
  private String ticker;

  @Schema(description = "회사명", example = "Coca-Cola Consolidated, Inc.")
  private String name;

  public CompanyEntity(Company company) {
    this.ticker = company.getTicker();
    this.name = company.getName();
  }
}
