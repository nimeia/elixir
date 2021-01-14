package company.project.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * spring boot-admin app 主入口
 *
 * @author huang
 */
@SpringBootApplication
@EnableAdminServer
@EnableEurekaServer()
public class MonitorApp {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.main(MonitorApp.class)
                .sources(new Class<?>[]{MonitorApp.class})
                .listeners(new ApplicationPidFileWriter())
                .build()
                .run(args);
    }
}
