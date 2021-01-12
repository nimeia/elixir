package company.project.job;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class JobApp {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.main(JobApp.class)
                .sources(new Class<?>[]{JobApp.class})
                .listeners(new ApplicationPidFileWriter())
                .build()
                .run(args);
    }
}
