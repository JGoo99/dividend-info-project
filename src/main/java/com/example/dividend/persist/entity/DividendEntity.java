package com.example.dividend.persist.entity;

import com.example.dividend.model.Dividend;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "dividend")
@Getter
@ToString
@NoArgsConstructor
@Schema(description = "Dividend DB")
public class DividendEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "배당금 식별 아이디", example = "1")
  private Long dividendId;

  @Schema(description = "회사 식별 아이디", example = "1")
  private Long companyId;

  @Schema(description = "배당금 지급 시점", example = "2023-12-03T00:00:00")
  private LocalDateTime date;

  @Schema(description = "배당금액", example = "0.25")
  private String dividend;

  public DividendEntity(Long companyId, Dividend dividend) {
    this.companyId = companyId;
    this.date = dividend.getDate();
    this.dividend = dividend.getDividend();
  }
}

