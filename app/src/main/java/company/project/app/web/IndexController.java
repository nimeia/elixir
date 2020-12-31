package company.project.app.web;

import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Object home(){
        logger.info("===google===");
        return "success";
    }

    @RequestMapping("errorTest")
    @ResponseBody
    public Object errorTest(){
        Sentry.captureException(new RuntimeException("google"));
        if(true){
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

}
