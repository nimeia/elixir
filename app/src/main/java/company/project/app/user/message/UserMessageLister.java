package company.project.app.user.message;

import company.project.app.user.vo.UserVo;
import company.project.core.utils.json.JsonUtils;
import company.project.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMessageLister {

    @Autowired
    UserService userService;

    @RabbitListener(queues = "syncUser")
    public void processMessage(String content) {
        log.info("syncUser message:{}", content);
        UserVo userVo = JsonUtils.toObject(content, UserVo.class);

        //doing something
    }
}
