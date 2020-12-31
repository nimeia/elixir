package company.project.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 应用的通用配置
 * 从core-config包中导入即可
 *
 * @author huang
 */
@Configuration
public class GlobalConfig {

    private static final Logger logger = LoggerFactory.getLogger(GlobalConfig.class);

    @PostConstruct
    void init() throws InterruptedException {
//        new Thread(){
//            @Override
//            public void run() {
//                while (true){
//                    logger.info("===========================");
//                    try {
//                        Thread.sleep(500L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();

    }
}
