package company.project.app.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
@Slf4j
public class RestTemplateConfig {

    @Bean
    RestTemplateCustomizer restTemplateCustomizer() {
        log.info("--config the http client log--");
        return new RestTemplateCustomizer() {
            @Override
            public void customize(RestTemplate restTemplate) {

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
                OkHttpClient client = new OkHttpClient.Builder()
//                        .addInterceptor(loggingInterceptor)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Long start = System.currentTimeMillis();
                                try {
                                    return chain.proceed(chain.request());
                                } finally {
                                    log.info("call api: {} ,use {} ms", chain.request().url().toString(), (System.currentTimeMillis() - start));
                                }
                            }
                        })
                        .build();

                OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(client);

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