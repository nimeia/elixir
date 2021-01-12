package company.project.core.config.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@ConfigurationProperties("core.cors")
@ToString
public class CorsProperties implements Serializable {

    /**
     * 是否启用
     */
    private Boolean enabled = true;

    private Boolean allowCredentials = true;

    private String allowedOrigin = "*";

    private String allowedHeader = "*";

    private String allowedMethod = "*";

    private String[] configPath ={"/api/**"};


}
