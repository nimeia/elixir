package company.project.app.user.web;

import company.project.app.api.UserApi;
import company.project.app.api.UserApiRequest;
import company.project.app.api.UserApiResponse;
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

    @Autowired
    UserApi userApi;

    @RequestMapping("test")
    public Object test(){
        HttpBinIPVo ipStr = restTemplate.getForObject("https://httpbin.org/ip", HttpBinIPVo.class);

        log.info("the ip of my service : {} ",ipStr);
        return ipStr;
    }

    @RequestMapping("retrofit")
    public Object retrofitTest(){
        UserApiRequest userApiRequest = UserApiRequest.builder()
                .displayName("huang")
                .email("xxx@ok.com")
                .enabled(true)
                .username("huang")
                .phone("13800013801")
                .build();
        UserApiResponse userApiResponse = userApi.addUser(userApiRequest);

        return userApiResponse;
    }

}
