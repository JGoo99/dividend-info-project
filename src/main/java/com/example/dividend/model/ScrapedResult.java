package com.example.dividend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

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

  public ScrapedResult() {
    this.dividends = new ArrayList<>();
  }
}
