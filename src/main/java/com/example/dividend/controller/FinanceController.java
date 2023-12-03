package com.example.dividend.controller;

import com.example.dividend.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor
@Tag(name = "finance", description = "Finance API")
public class FinanceController {

  private final FinanceService financeService;

  @GetMapping("/dividend/{companyName}")
  @Operation(summary = "get finance data", description = "회사정보와 배당금 정보를 불러온다.")
  public ResponseEntity<?> searchFinance(
    @Schema(name = "회사명", example = "3M Company")
    @PathVariable String companyName) {

    var dividend = this.financeService.getDividendByCompanyName(companyName);
    return ResponseEntity.ok(dividend);
  }

}
