package company.project.core.config.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ToString
@ConfigurationProperties(prefix = "core.okhttp")
public class OkHttpProperties {

    private Boolean logContext = false;

}
