package com.keton.server.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
@Component
@ConfigurationProperties("entity.set.envment")
@Data
public class EnvironmentEntity {
    private String userName;
    private String password;
    private HostEntity host;

}
