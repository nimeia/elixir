package company.project.core.config.springsecurity;

import cn.hutool.core.util.IdUtil;
import company.project.api.base.response.ApiSimpleResponse;
import company.project.core.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有凭证时调用
 *
 * @author huang
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("AccessDeniedHandler call ...");
        }
        // GET请求跳转到主页
        if (HttpMethod.GET.toString().equalsIgnoreCase(request.getMethod())
                && request.getHeader("Accept") != null
                && !request.getHeader("Accept").contains("application/json")) {
            response.sendRedirect(request.getContextPath() + "/login?need=true");
        } else {
            // POST请求返回json
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");

            ApiSimpleResponse apiSimpleResponse = new ApiSimpleResponse()
                    .requestId(IdUtil.objectId())
                    .code(ApiSimpleResponse.RESPONSE_CODE_ERROR)
                    .system("app")
                    .data("")
                    .success(false)
                    .message("you need to login first!")
                    .businessMessage("not allow!");

            response.getWriter().write(JsonUtils.toJson(apiSimpleResponse));
        }
    }
}
