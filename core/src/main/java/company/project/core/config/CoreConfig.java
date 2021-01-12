package company.project.core.config;

import company.project.core.config.properties.CorsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
        CorsProperties.class
})
@Configuration
public class CoreConfig {
}
