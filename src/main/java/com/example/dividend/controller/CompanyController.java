package com.example.dividend.controller;

import com.example.dividend.model.Company;
import com.example.dividend.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
@Tag(name = "Company", description = "Company API")
public class CompanyController {

  private final CompanyService companyService;

  @GetMapping("/autocomplete")
  public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
    return null;
  }

  @GetMapping
  public ResponseEntity<?> searchCompany() {
    return null;
  }

  @PostMapping
  @Operation(summary = "add company data", description = "회사 정보를 저장한다.")
  public ResponseEntity<?> addCompany(
    @Schema(name = "회사정보", implementation = Company.class, example = "MMM")
    @RequestBody Company request) {

    String ticker = request.getTicker().trim();
    if (ObjectUtils.isEmpty(ticker)) {
      throw new RuntimeException("ticker is empty");
    }

    Company company = this.companyService.save(ticker);
    return ResponseEntity.ok(company);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteCompany(@RequestParam String ticker) {
    return null;
  }
}
