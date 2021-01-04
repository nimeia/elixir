package company.project.app.web;

import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 入口
 *
 * @author huang
 */
@Controller
@RequestMapping("")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping()
    @ResponseBody
    public Object home() {
        logger.info("===google===");
        return "success";
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("errorTest")
    @ResponseBody
    public Object errorTest() {
//        Sentry.captureException(new RuntimeException("google"));
        jdbcTemplate.execute("select * from user where id = 1000 ");
        jdbcTemplate.execute("select * from user where id = 2000 ");

        if (true) {
//            try {
            throw new RuntimeException("错误===777");
//            } catch (RuntimeException e) {
////                Sentry.captureException(e);
//                logger.error(e.getMessage(),e);
//            }
//            return true;
        }
        return false;
    }


    @Autowired
    StringEncryptor stringEncryptor;


    @Value("${xxx:baidu}")
    String xxx;

    @ResponseBody
    @RequestMapping("encrypt")
    public Object encrypt() {
        String goooo = stringEncryptor.encrypt("app");

        return goooo + " : " + stringEncryptor.decrypt(goooo) +" : "+xxx;
    }

}
