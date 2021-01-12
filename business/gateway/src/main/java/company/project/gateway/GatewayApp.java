package company.project.gateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class GatewayApp {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.main(GatewayApp.class)
                .sources(new Class<?>[]{GatewayApp.class})
                .listeners(new ApplicationPidFileWriter())
                .build()
                .run(args);
    }
}
