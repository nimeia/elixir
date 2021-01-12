package company.project.app.config.properties;

import java.io.Serializable;

/**
 * custom spring security config
 *
 * @author huang
 */
public class SecurityProperties implements Serializable {

    /**
     * 不做安全校验url
     */
    private String[] ignoreUrl;


}
