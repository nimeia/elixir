package company.project.core.config;

import company.project.core.config.properties.OkHttpProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "core.okhttp", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(name = {"okhttp3.OkHttpClient"})
@EnableConfigurationProperties(OkHttpProperties.class)
public class OkHttpConfig {

    @Autowired
    OkHttpProperties okHttpProperties;

    @Bean
    OkHttpClient okHttpClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
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
                });
        if (okHttpProperties.getLogContext()) {
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder.build();
        return client;
    }
}
