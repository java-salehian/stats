package com.example.stats.repository;

import com.example.stats.entity.RecommenderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommenderRepository extends CrudRepository<RecommenderEntity, String> {
    RecommenderEntity findByAppIdAndCountryCodeAndAdvertiserId(int appId, String countryCode, int advertiserId);

    @Query("SELECT DISTINCT e.appId, e.countryCode FROM recommender e")
    List<String[]> findDistinctAppIdAndCountryCodes();

    @Query(value = "SELECT * FROM recommender e where e.app_id = ?1 and e.country_code = ?2 ORDER BY (revenue/impressions) DESC LIMIT 5", nativeQuery = true)
    List<RecommenderEntity> findTop5RevenuePerImpressionsByAppIdAndCountryCode(int appId, String countryCode);

}