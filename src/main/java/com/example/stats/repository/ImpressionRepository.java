package com.example.stats.repository;

import com.example.stats.entity.ImpressionEntity;
import org.springframework.data.repository.CrudRepository;

public interface ImpressionRepository extends CrudRepository<ImpressionEntity, String> {
    ImpressionEntity findByIdIs(String id);
}