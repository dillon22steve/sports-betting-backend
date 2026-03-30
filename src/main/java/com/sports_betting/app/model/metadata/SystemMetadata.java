package com.sports_betting.app.model.metadata;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class SystemMetadata {

    @Id
    private String configKey; // e.g., "LAST_GAME_SYNC"
    
    private LocalDateTime configValue;

    // Constructors
    public SystemMetadata() {}
    
    public SystemMetadata(String configKey, LocalDateTime configValue) {
        this.configKey = configKey;
        this.configValue = configValue;
    }

    // Getters and Setters
    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }
    public LocalDateTime getConfigValue() { return configValue; }
    public void setConfigValue(LocalDateTime configValue) { this.configValue = configValue; }
    
}
