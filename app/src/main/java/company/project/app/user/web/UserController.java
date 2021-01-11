package company.project.app.user.web;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import company.project.app.user.validate.usergroup.UserInsert;
import company.project.app.user.mapper.UserVoMapper;
import company.project.app.user.vo.UpdatePasswordVo;
import company.project.app.user.vo.UserVo;
import company.project.service.user.UserService;
import company.project.service.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.Locale;

/**
 * user api
 */
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * query user
     * @param username
     * @return
     */
    @RequestMapping()
    public UserVo userInfo(@RequestParam("username") @Valid @NotBlank(message = "用户名不能为空") String username) {
        UserDto user = userService.findUser(username);
        return UserVoMapper.mapper.toUserVo(user);
    }

    /**
     * insert new user
     * @param userVo
     * @return
     */
    @RequestMapping("addUser")
    public UserVo addUser(@Validated(value = {UserInsert.class, Default.class}) @RequestBody UserVo userVo) {
        UserDto userDtoNew= UserVoMapper.mapper.toUserDto(userVo);
        //set the init data that will not input by the api
        userDtoNew.setCreateBy("web sys");
        userDtoNew.setLoginFailTimes(0);
        userDtoNew.setValidTime(DateUtil.offset(new Date(), DateField.YEAR,1));
        userDtoNew.setCreateTime(new Date());
        userDtoNew.setLocked(false);
        //set the init password
        userDtoNew.setPassword(passwordEncoder.encode("000000"));
        UserDto userDto = userService.insertUser(userDtoNew);

        return UserVoMapper.mapper.toUserVo(userDto);
    }

    @Autowired
    MessageSource messageSource;
    /**
     * update password
     * @param passwordVo
     * @return
     */
    @RequestMapping("updatePassword")
    public Object updatePassword(@RequestBody @Valid UpdatePasswordVo passwordVo){

        //you can use messageSource to get local message
        String message = messageSource.getMessage("update.password.username",new String[]{},"xxxxx", Locale.CHINESE);;

        return message;
    }


}
