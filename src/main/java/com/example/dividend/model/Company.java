package com.example.dividend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회사 정보")
public class Company {
  @Schema(description = "회사 코드", example = "COKE")
  private String ticker;

  @Schema(description = "회사명", example = "Coca-Cola Consolidated, Inc.")
  private String name;
}
