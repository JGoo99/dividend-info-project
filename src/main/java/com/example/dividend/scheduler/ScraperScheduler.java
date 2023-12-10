package com.example.dividend.scheduler;

import com.example.dividend.model.Company;
import com.example.dividend.model.ScrapedResult;
import com.example.dividend.model.constants.CacheKey;
import com.example.dividend.persist.entity.CompanyEntity;
import com.example.dividend.persist.entity.DividendEntity;
import com.example.dividend.persist.repository.CompanyRepository;
import com.example.dividend.persist.repository.DividendRepository;
import com.example.dividend.scraper.YahooFinanceScraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableCaching
@AllArgsConstructor
public class ScraperScheduler {

  private final YahooFinanceScraper yahooFinanceScraper;

  private final CompanyRepository companyRepository;
  private final DividendRepository dividendRepository;

  @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
  @Scheduled(cron = "${scheduler.scrap.yahoo}")
  public void yahooFinanceScheduling() {
    List<CompanyEntity> companyEntities = this.companyRepository.findAll();

    for (var entity : companyEntities) {
      log.info("scraping scheduler is started -> " + entity.getName());

      ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(
        new Company(entity.getTicker(), entity.getName()));

      scrapedResult.getDividends().stream()
        .map(e -> new DividendEntity(entity.getCompanyId(), e))
        .forEach(e -> {
            boolean exists =
              dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
            if (!exists) {
              this.dividendRepository.save(e);
              log.info("insert new dividend -> " + e.toString());
            }
          }
        );

      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
