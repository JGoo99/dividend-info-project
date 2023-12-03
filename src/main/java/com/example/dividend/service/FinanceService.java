package com.example.dividend.service;

import com.example.dividend.model.Company;
import com.example.dividend.model.Dividend;
import com.example.dividend.model.ScrapedResult;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.persist.entity.DividendEntity;
import com.example.dividend.persist.repository.CompanyRepository;
import com.example.dividend.persist.repository.DividendRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FinanceService {

  private final CompanyRepository companyRepository;
  private final DividendRepository dividendRepository;

  public ScrapedResult getDividendByCompanyName(String companyName) {
    CompanyEntity company = companyRepository.findByName(companyName)
      .orElseThrow(() ->
        new RuntimeException("Not Found CompanyName -> " + companyName));

    List<DividendEntity> dividendEntities =
      this.dividendRepository.findByCompanyId(company.getCompanyId());

    List<Dividend> dividends = new ArrayList<>();
    for (var entity : dividendEntities) {
      dividends.add(Dividend.builder()
        .date(entity.getDate())
        .dividend(entity.getDividend())
        .build());
    }

    return new ScrapedResult(
      Company.builder()
        .ticker(company.getTicker())
        .name(company.getName())
        .build(),

      dividends
      );
  }
}
