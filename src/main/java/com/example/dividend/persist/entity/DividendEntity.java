package com.example.dividend.persist.entity;

import com.example.dividend.model.Dividend;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "dividend")
@Getter
@ToString
@NoArgsConstructor
@Table(
  uniqueConstraints = {
    @UniqueConstraint(
      columnNames = {"companyId", "date"}
    )
  }
)
public class DividendEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long dividendId;

  private Long companyId;

  private LocalDateTime date;

  private String dividend;

  public DividendEntity(Long companyId, Dividend dividend) {
    this.companyId = companyId;
    this.date = dividend.getDate();
    this.dividend = dividend.getDividend();
  }
}

