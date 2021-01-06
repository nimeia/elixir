package company.project.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import company.project.api.base.response.ApiSimpleResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 业务应用 app 入口
 *
 * @author huang
 */
@SpringBootApplication
public class ProjectApp {

    public static void main(String[] args) throws JsonProcessingException {
//        SpringApplicationBuilder builder = new SpringApplicationBuilder();
//        builder.main(ProjectApp.class)
//                .sources(new Class<?>[]{ProjectApp.class})
//                .listeners(new ApplicationPidFileWriter())
//                .build()
//                .run(args);

        SpringApplication.run(ProjectApp.class, args);
    }
}
