package company.project.core.config.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("core.open-api")
@Component
@Data
@ToString
public class OpenApiProperties implements Serializable {

    public OpenApiProperties() {
        initParameter.put("confPath", "api/apiConfig.xml");
        initParameter.put("statusPath", "/_api/_status");
        initParameter.put("statusEnabled", "true");
        initParameter.put("logLevel", "INFO");
    }

    private Boolean enabled = true;

    String[] urls = {"/_api/*"};

    Map<String, String> initParameter = new HashMap<>();
}
