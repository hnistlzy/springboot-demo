package com.keton.server.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
@Component
@ConfigurationProperties(prefix = "entity.set.envment.host")
@Data
public class HostEntity {
    private String address;
    private String profile;
}
