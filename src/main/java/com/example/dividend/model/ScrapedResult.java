package com.example.dividend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@Schema(description = "스크랩 데이터")
public class ScrapedResult {
  @Schema(description = "회사 정보", implementation = Company.class)
  private Company company;

  @Schema(description = "배당금 목록", implementation = Dividend.class)
  private List<Dividend> dividends;

  @JsonCreator
  public ScrapedResult() {
    this.dividends = new ArrayList<>();
  }
}
