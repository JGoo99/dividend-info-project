package com.example.dividend.service;

import com.example.dividend.model.Company;
import com.example.dividend.model.ScrapedResult;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.persist.entity.DividendEntity;
import com.example.dividend.persist.repository.CompanyRepository;
import com.example.dividend.persist.repository.DividendRepository;
import com.example.dividend.scraper.Scraper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {

  public final Scraper yahooFinanceScraper;

  private final CompanyRepository companyRepository;
  private final DividendRepository dividendRepository;

  public Company save(String ticker) {
    boolean exists = this.companyRepository.existsByTicker(ticker);
    if (exists) {
      throw new RuntimeException("already exists ticker -> " + ticker);
    }
    return this.storeCompanyAndDividend(ticker);
  }

  public Company storeCompanyAndDividend(String ticker) {
    Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
    if (ObjectUtils.isEmpty(company)) {
      throw new RuntimeException("failed to scrap company by ticker -> " + ticker);
    }

    ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

    CompanyEntity companyEntity = this.companyRepository.save(new CompanyEntity(company));
    List<DividendEntity> dividendEntities =
      scrapedResult.getDividends().stream()
        .map(e -> new DividendEntity(companyEntity.getCompanyId(), e))
        .toList();

    this.dividendRepository.saveAll(dividendEntities);

    return company;
  }
}
