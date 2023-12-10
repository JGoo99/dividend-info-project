package com.example.dividend.service;

import com.example.dividend.exception.impl.NotFoundCompanyException;
import com.example.dividend.model.Company;
import com.example.dividend.model.Dividend;
import com.example.dividend.model.ScrapedResult;
import com.example.dividend.model.constants.CacheKey;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.persist.entity.DividendEntity;
import com.example.dividend.persist.repository.CompanyRepository;
import com.example.dividend.persist.repository.DividendRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FinanceService {

  private final CompanyRepository companyRepository;
  private final DividendRepository dividendRepository;

  @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
  public ScrapedResult getDividendByCompanyName(String companyName) {
    CompanyEntity company = companyRepository.findByName(companyName)
      .orElseThrow(() -> new NotFoundCompanyException());

    List<DividendEntity> dividendEntities =
      this.dividendRepository.findByCompanyId(company.getCompanyId());

    List<Dividend> dividends = new ArrayList<>();
    for (var entity : dividendEntities) {
      dividends.add(new Dividend(entity.getDate(), entity.getDividend()));
    }

    return new ScrapedResult(
      new Company(company.getTicker(), company.getName()), dividends);
  }
}
