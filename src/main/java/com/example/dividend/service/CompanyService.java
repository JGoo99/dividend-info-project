package com.example.dividend.service;

import com.example.dividend.exception.impl.AlreadyExistCompanyException;
import com.example.dividend.exception.impl.NotFoundCompanyException;
import com.example.dividend.model.Company;
import com.example.dividend.model.ScrapedResult;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.persist.entity.DividendEntity;
import com.example.dividend.persist.repository.CompanyRepository;
import com.example.dividend.persist.repository.DividendRepository;
import com.example.dividend.scraper.Scraper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

  private final Trie trie;
  public final Scraper yahooFinanceScraper;

  private final CompanyRepository companyRepository;
  private final DividendRepository dividendRepository;

  public Company save(String ticker) {
    boolean exists = this.companyRepository.existsByTicker(ticker);
    if (exists) {
      throw new AlreadyExistCompanyException();
    }
    return this.storeCompanyAndDividend(ticker);
  }

  public Page<CompanyEntity> getAllCompany(Pageable pageable) {
    return this.companyRepository.findAll(pageable);
  }

  private Company storeCompanyAndDividend(String ticker) {
    Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
    if (ObjectUtils.isEmpty(company)) {
      throw new NotFoundCompanyException();
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

  public List<String> getCompanyNamesByKeyword(String keyword) {
    Pageable limit = PageRequest.of(0, 10);

    Page<CompanyEntity> companyEntities =
      this.companyRepository.findByNameStartingWithIgnoreCase(keyword, limit);

    return companyEntities.stream()
      .map(e -> e.getName())
      .collect(Collectors.toList());
  }

  public void addAutocompleteKeyword(String keyword) {
    this.trie.put(keyword, null);
  }

  public List<String> autocomplete(String keyword) {
    return (List<String>) this.trie.prefixMap(keyword).keySet().stream()
      .limit(10)
      .collect(Collectors.toList());
  }

  public void deleteAutocompleteKeyword(String keyword) {
    this.trie.remove(keyword);
  }

  @Transactional
  public String deleteCompany(String ticker) {
    CompanyEntity companyEntity = this.companyRepository.findByTicker(ticker)
      .orElseThrow(() -> new NotFoundCompanyException());

    this.dividendRepository.deleteAllByCompanyId(companyEntity.getCompanyId());
    this.companyRepository.delete(companyEntity);
    this.deleteAutocompleteKeyword(companyEntity.getName());
    return companyEntity.getName();
  }
}
