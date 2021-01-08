package company.project.app.user.web;

import company.project.app.config.validate.usergroup.UserInsert;
import company.project.app.user.mapper.UserVoMapper;
import company.project.app.user.vo.UserVo;
import company.project.service.user.UserService;
import company.project.service.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.groups.Default;

/**
 * 用户接口
 */
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping()
    public UserVo userInfo(@RequestParam("username") String username) {
        UserDto user = userService.findUser(username);
        return UserVoMapper.mapper.toUserVo(user);
    }

    @RequestMapping("addUser")
    public UserVo addUser(@Validated(value = {UserInsert.class, Default.class}) @RequestBody UserVo userVo) {
        return userVo;
    }


}
