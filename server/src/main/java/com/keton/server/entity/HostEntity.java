package com.keton.server.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "entity.set.envment.host")
@Data
public class HostEntity {
    private String address;
    private String profile;
}
