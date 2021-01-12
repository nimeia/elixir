package company.project.app.config.springsecurity;

import cn.hutool.core.util.IdUtil;
import company.project.api.base.response.ApiSimpleResponse;
import company.project.core.utils.web.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理
 *
 * @author huang
 */
@Component
public class CustomAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailHandler.class);


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ApiSimpleResponse apiSimpleResponse = new ApiSimpleResponse().requestId(IdUtil.objectId()).code("500").success(false).system("app").message("not allow!").data(e.getMessage());
        String username = request.getParameter("username");

        logger.error("user " + username + " login failure ", e);
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {

            logger.error("user {} anthenticaton failure with reason :{}", username, e.getMessage());
            //todo 记录登录失败次数，进行用户锁定等调用

            ResponseUtils.jsonResponse(response, apiSimpleResponse.businessMessage("用户名或密码错误"));
        } else if (e instanceof DisabledException) {
            ResponseUtils.jsonResponse(response, apiSimpleResponse.businessMessage("账户被禁用，请联系管理员"));
        } else {
            ResponseUtils.jsonResponse(response, apiSimpleResponse.businessMessage("登录失败，其他内部错误"));
        }
    }
}
