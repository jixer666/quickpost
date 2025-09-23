package com.abc.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "quickpost")
public class AppConfigProperties {

    private List<String> whiteUrlList;


}
