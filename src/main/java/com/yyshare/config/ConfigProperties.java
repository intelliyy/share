package com.yyshare.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "dao")
public class ConfigProperties {

    private Map<String, String> executors;
    private String def;
}
