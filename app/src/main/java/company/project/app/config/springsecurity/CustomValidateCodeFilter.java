package company.project.app.config.springsecurity;

import cn.hutool.core.util.IdUtil;
import com.hazelcast.internal.util.StringUtil;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import company.project.api.base.response.ApiSimpleResponse;
import company.project.core.utils.web.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Slf4j
public class CustomValidateCodeFilter extends OncePerRequestFilter {

    @Value("${validCodeImgUrl:/login/validateCode}")
    private String validCodeImgUrl;

    @Value("${loginUrl:/login/process}")
    private String loginUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestUrl = request.getRequestURI();
        if (StringUtil.equalsIgnoreCase(requestUrl, validCodeImgUrl)) {
            // 使用gif验证码
            GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
            CaptchaUtil.out(gifCaptcha, request, response);

            return;
        } else {
            if (StringUtil.equalsIgnoreCase(requestUrl, loginUrl)) {
                String validateCode = request.getParameter("validateCode");
                if (!CaptchaUtil.ver(validateCode, request)) {
                    CaptchaUtil.clear(request);  // 清除session中的验证码
                    ResponseUtils.jsonResponse(response,
                            new ApiSimpleResponse<String>()
                                    .message("validateCode not right!")
                                    .code(ApiSimpleResponse.RESPONSE_CODE_SUCCESS)
                                    .system("app")
                                    .data("")
                                    .success(true)
                                    .requestId(IdUtil.objectId())
                                    .businessMessage("验证码不正确"));
                    return;
                }
            }
            filterChain.doFilter(request, response);
        }
    }
}
