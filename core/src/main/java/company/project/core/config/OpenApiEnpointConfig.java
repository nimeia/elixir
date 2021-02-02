package company.project.core.config;

import company.project.core.config.properties.OpenApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.DispatcherType;
import java.util.Arrays;

@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "core.open-api", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(name = {"org.tuckey.web.filters.urlrewrite.UrlRewriteFilter"})
@EnableConfigurationProperties(value = {OpenApiProperties.class})
public class OpenApiEnpointConfig {

    public OpenApiEnpointConfig() {
        log.info("...OpenApiEnpointConfig...");
    }

    @Autowired
    OpenApiProperties openApiProperties;

    @Bean
    FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(0);
        filterRegistrationBean.setEnabled(true);

        UrlRewriteFilter urlRewriteFilter = new UrlRewriteFilter();

        filterRegistrationBean.setFilter(urlRewriteFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList(openApiProperties.getUrls()));
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
        if (openApiProperties.getInitParameter() != null && openApiProperties.getInitParameter().size() > 0) {
            for (String key : openApiProperties.getInitParameter().keySet()) {
                filterRegistrationBean.addInitParameter(key, openApiProperties.getInitParameter().get(key));
            }
        }

        return filterRegistrationBean;
    }
}
