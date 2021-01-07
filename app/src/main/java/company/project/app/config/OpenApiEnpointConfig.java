package company.project.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.DispatcherType;
import java.util.Arrays;

@Configuration
public class OpenApiEnpointConfig {

    @Bean
    FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setOrder(0);
        filterRegistrationBean.setEnabled(true);

        UrlRewriteFilter urlRewriteFilter = new UrlRewriteFilter();

        filterRegistrationBean.setFilter(urlRewriteFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/_api/*"));
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.FORWARD);
        filterRegistrationBean.addInitParameter("confPath","api/apiConfig.xml");
        filterRegistrationBean.addInitParameter("statusPath","/_api/_status");
        filterRegistrationBean.addInitParameter("statusEnabled","true");
        filterRegistrationBean.addInitParameter("logLevel","INFO");

        return filterRegistrationBean;
    }
}
