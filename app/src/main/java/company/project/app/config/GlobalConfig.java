package company.project.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    void init() throws InterruptedException {
//        new Thread(){
//            @Override
//            public void run() {
//                while (true){
//                    logger.info("===========================");
//                    jdbcTemplate.queryForMap("select * from user where id = ? ",1000);
//                    logger.info("---------------------------");
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
