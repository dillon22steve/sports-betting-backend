package com.sports_betting.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sports_betting.app.model.metadata.SystemMetadata;

import java.util.Optional;


@Repository
public interface MetadataRepository extends JpaRepository<SystemMetadata, String> {
    
    // Standard findById works since configKey is the @Id
    // But we can add a helper to make the service code cleaner
    default void updateTimestamp(String key, java.time.LocalDateTime time) {
        save(new SystemMetadata(key, time));
    }
}
