package company.project.core.config;

import company.project.core.config.properties.RestTemplateProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "core.rest", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(RestTemplateProperties.class)
public class RestTemplateConfig {

    public RestTemplateConfig() {
        log.info("start RestTemplateConfig ...");
    }

    @Autowired
    RestTemplateProperties restTemplateProperties;

    @Autowired
    OkHttpClient okHttpClient;

    @Bean
    RestTemplateCustomizer restTemplateCustomizer() {
        log.info("--config the http client log--");
        return new RestTemplateCustomizer() {
            @Override
            public void customize(RestTemplate restTemplate) {
                OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(okHttpClient);

                restTemplate.setRequestFactory(okHttp3ClientHttpRequestFactory);

            }
        };
    }

    @Bean
    RestTemplate restTemplate(@Autowired RestTemplateBuilder restTemplateBuilder) {
        log.info("-- init a restTemplate--");
        return restTemplateBuilder.build();
    }


}
