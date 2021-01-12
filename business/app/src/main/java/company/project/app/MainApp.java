package company.project.app;

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
public class MainApp {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.main(MainApp.class)
                .sources(new Class<?>[]{MainApp.class})
                .listeners(new ApplicationPidFileWriter())
                .build()
                .run(args);
    }
}
