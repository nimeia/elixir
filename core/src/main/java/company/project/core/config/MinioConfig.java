package company.project.core.config;

import company.project.core.config.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.checkerframework.framework.qual.PostconditionAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
@ConditionalOnProperty(prefix = "core.minio", name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
@ConditionalOnClass(MinioClient.class)
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfig {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    @ConditionalOnMissingBean
    MinioClient minioClient(OkHttpClient okHttpClient) {
        log.info("init the minio client user args :{}", minioProperties);
        MinioClient client = MinioClient.builder()
                .httpClient(okHttpClient)
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .endpoint(minioProperties.getUrl()).build();
        client.setTimeout(minioProperties.getConnectTimeout().toMinutes(),
                minioProperties.getWriteTimeout().toMinutes(),
                minioProperties.getReadTimeout().toMinutes());

        return client;
    }

    @Bean
    @ConditionalOnMissingBean(OkHttpClient.class)
    OkHttpClient httpClient() {
        log.info("minio init by the default http client");
        return new OkHttpClient.Builder().build();
    }

    @PostConstruct
    void init() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {

        if (minioProperties.getBucket() == null || "".equals(minioProperties.getBucket().trim())) {
            return;
        }
        if (minioProperties.isCreateBucket()) {
            MinioClient minioClient = minioClient(httpClient());
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
            if (!bucketExists) {
                log.info("create minio bucket : {} ", minioProperties.getBucket());
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
            }
        }
    }

}
