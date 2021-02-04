package company.project.core.config.springsecurity;

import cn.hutool.core.util.IdUtil;
import company.project.api.base.response.ApiSimpleResponse;
import company.project.core.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录信息失效时调用
 *
 * @author huang
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.info("CustomAccessDeniedHandler call ...");
        // POST请求返回json
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        ApiSimpleResponse apiSimpleResponse = new ApiSimpleResponse()
                .requestId(IdUtil.objectId())
                .code(ApiSimpleResponse.RESPONSE_LOGIN_NEED)
                .system("app")
                .data("")
                .success(false)
                .message(accessDeniedException.getMessage())
                .businessMessage("not allow!");

        response.getWriter().write(JsonUtils.toJson(apiSimpleResponse));
    }
}
