package company.project.core.config.properties;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * rest template
 */
@Data
@ToString
@ConfigurationProperties("core.rest")
@Component
public class RestTemplateProperties implements Serializable {

    /**
     * 是否启用
     */
    private Boolean enabled = true;

    /**
     * 是否记录http请求日志
     */
    private Boolean logContext = true;

}
