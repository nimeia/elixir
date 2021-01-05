package company.project.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.context.WebServerPortFileWriter;

/**
 * 业务应用 app 入口
 *
 * @author huang
 */
@SpringBootApplication
public class ProjectApp {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.main(ProjectApp.class)
                .sources(new Class<?>[]{ProjectApp.class})
                .listeners(new ApplicationPidFileWriter())
                .build()
                .run(args);
    }
}
