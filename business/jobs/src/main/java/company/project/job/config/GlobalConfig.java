package company.project.job.config;

import company.project.service.job.UserSyncJobHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "company.project.service.job"
})
public class GlobalConfig {

    @Bean
    UserSyncJobHandler userSyncJobHandler(){
        return new UserSyncJobHandler();
    }
}
