package company.project.core.config.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * custom spring security config
 *
 * @author huang
 */
@Data
@Component
@ConfigurationProperties("core.security")
@ToString
public class SecurityProperties implements Serializable {

    private Boolean enabled = true;

    private Boolean validCodeEnabled = false;

    /**
     * 不做安全校验url
     */
    private String[] ignoreUrl = {
            "/*.html",
            "/favicon.ico",
            "/**/*.htm",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.jpg",
            "/**/*.webp",
            "/**/*.mp4",
            "/**/*.eot",
            "/**/*.woff2",
            "/**/*.woff",
            "/**/*.ttf",
            "/druid/**",
            "/_api/**",
            "/actuator/**",
            "/h2-console/**"
    };

    private String loginPage = "/login";

    private String loginProcessUrl = "/login/process";

    private String failureForwardUrl = "/login/error";

    private String defaultSuccessUrl = "/login/success";

    private String validCodeImgUrl = "/login/validateCode";
}
