package company.project.app.config.springsecurity;

import company.project.api.base.response.ApiSimpleResponse;
import company.project.core.utils.web.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * authentication success business handler
 *
 * @author huang
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        logger.info("user {} login success! ", username);
        //todo 处理登录成功后的相关日志，统计工作

        //create jwt
        ApiSimpleResponse tokenResponse = new ApiSimpleResponse<>()
                .businessMessage("登录成功")
                .code("200")
                .requestId(String.valueOf(System.currentTimeMillis()))
                .system("app")
                .success(true)
                .data(request.getSession().getId())
                .message("login success");

        ResponseUtils.jsonResponse(response, tokenResponse);

    }
}
