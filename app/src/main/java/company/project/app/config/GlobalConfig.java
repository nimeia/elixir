package company.project.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 应用的通用配置
 * 从core-config包中导入即可
 *
 * @author huang
 */
@Configuration
@EnableCaching
@EntityScan(basePackages = {
        "company.project.model.user.entity"
})
@EnableJpaRepositories(basePackages = {
        "company.project.model.user.repos"
})
@ComponentScan(basePackages = {
        "company.project.service.user"
})
@Import(
        {cn.hutool.extra.spring.SpringUtil.class}
)

public class GlobalConfig {

    private static final Logger logger = LoggerFactory.getLogger(GlobalConfig.class);
}
