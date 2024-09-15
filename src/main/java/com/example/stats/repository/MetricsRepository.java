package com.example.stats.repository;

import com.example.stats.entity.MetricsEntity;
import org.springframework.data.repository.CrudRepository;

public interface MetricsRepository extends CrudRepository<MetricsEntity, String> {
    MetricsEntity findByAppIdAndCountryCode(int appId, String countryCode);
}