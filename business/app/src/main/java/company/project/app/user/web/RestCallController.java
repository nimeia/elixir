package company.project.app.user.web;

import company.project.app.user.vo.HttpBinIPVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * rest Template demo
 */
@RequestMapping("restCall")
@RestController
@Slf4j
public class RestCallController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("test")
    public Object test(){
        HttpBinIPVo ipStr = restTemplate.getForObject("https://httpbin.org/ip", HttpBinIPVo.class);


        log.info("the ip of my service : {} ",ipStr);
        return ipStr;
    }

}
