package com.example.dividend.controller;

import com.example.dividend.model.Company;
import com.example.dividend.model.constants.CacheKey;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
@Tag(name = "Company", description = "Company API")
public class CompanyController {

  private final CompanyService companyService;
  private final CacheManager cacheManager;

  @GetMapping("/autocomplete")
  @Operation(summary = "autocomplete search", description = "회사명의 자동 완성 검색 기능이다.")
  public ResponseEntity<?> autocomplete(
    @Schema(name = "키워드(접두사)", example = "CO")
    @RequestParam String keyword) {
//    var companyNames = this.companyService.autocomplete(keyword);
    var companyNames =
      this.companyService.getCompanyNamesByKeyword(keyword);
    return ResponseEntity.ok(companyNames);
  }

  @GetMapping
  @PreAuthorize("hasRole('READ')")
  @Operation(summary = "get company list", description = "관리하고있는 모든 회사 정보를 불러온다.")
  public ResponseEntity<?> searchCompany(
    @Schema(name = "페이징", implementation = Pageable.class, example = "?size=5&page=0")
    final Pageable pageable) {

    Page<CompanyEntity> companies = this.companyService.getAllCompany(pageable);
    return ResponseEntity.ok(companies);
  }

  @PostMapping
  @PreAuthorize("hasRole('WRITE')")
  @Operation(summary = "add company and dividend data", description = "새로운 회사와 배당금 정보를 저장한다.")
  public ResponseEntity<?> addCompany(
    @Schema(name = "회사정보", implementation = Company.class, example = "MMM")
    @RequestBody Company request) {

    String ticker = request.getTicker().trim();
    if (ObjectUtils.isEmpty(ticker)) {
      throw new RuntimeException("ticker is empty");
    }

    Company company = this.companyService.save(ticker);
    // this.companyService.addAutocompleteKeyword(company.getName());
    return ResponseEntity.ok(company);
  }

  @DeleteMapping("/{ticker}")
  @PreAuthorize("hasRole('WRITE')")
  @Operation(summary = "delete company and dividend data", description = "회사와 배당금 정보를 삭제한다.")
  public ResponseEntity<?> deleteCompany(
    @Schema(name = "회사코드", example = "MMM")
    @PathVariable String ticker) {

    String companyName = this.companyService.deleteCompany(ticker);
    this.clearFinanceCache(companyName);
    return ResponseEntity.ok(companyName);
  }

  public void clearFinanceCache(String companyName) {
    this.cacheManager.getCache(CacheKey.KEY_FINANCE).evict(companyName);
  }
}
