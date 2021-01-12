package company.project.core.config;

import company.project.core.config.properties.CorsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * cors config
 *
 * @author huang
 */
@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "core.cors", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CorsConfig {

    @Autowired
    CorsProperties corsProperties;

    @Bean
    public CorsFilter corsFilter() {
        log.info("start to config using :{}", corsProperties);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(corsProperties.getAllowCredentials());
        config.addAllowedOrigin(corsProperties.getAllowedOrigin());
        config.addAllowedHeader(corsProperties.getAllowedHeader());
        config.addAllowedMethod(corsProperties.getAllowedMethod());

        if (corsProperties.getConfigPath() != null && corsProperties.getConfigPath().length > 0) {
            for (String path : corsProperties.getConfigPath()) {
                source.registerCorsConfiguration(path, config);
            }
        }
        return new CorsFilter(source);
    }
}
