package company.project.core.config.properties;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
@ConfigurationProperties("core.hazelcast")
public class HazelcastProperties {

    private Boolean enabled = true;

    private String appName = "spring-boot-app";

    private String[] hazelcastIps;

}
