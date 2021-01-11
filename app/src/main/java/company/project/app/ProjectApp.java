package company.project.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 业务应用 app 入口
 *
 * @author huang
 */
@SpringBootApplication
@EnableCaching

public class ProjectApp {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.main(ProjectApp.class)
                .sources(new Class<?>[]{ProjectApp.class})
                .listeners(new ApplicationPidFileWriter())
                .build()
                .run(args);

//        SpringApplication.run(ProjectApp.class, args);
    }
}
