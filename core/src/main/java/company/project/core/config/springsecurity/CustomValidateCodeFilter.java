package company.project.core.config.springsecurity;

import cn.hutool.core.util.IdUtil;
import com.hazelcast.internal.util.StringUtil;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import company.project.api.base.response.ApiSimpleResponse;
import company.project.core.config.properties.SecurityProperties;
import company.project.core.utils.web.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class CustomValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!securityProperties.getValidCodeEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestUrl = request.getRequestURI();
        if (StringUtil.equalsIgnoreCase(requestUrl, securityProperties.getValidCodeImgUrl())) {
            String base64 = request.getParameter("base64");
            if (base64 != null && !base64.equalsIgnoreCase("false")) {
                GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
                String sessionId = request.getSession().getId();
                Map map = new HashMap();
                map.put("X-Auth-Token", sessionId);
                map.put("verCode", gifCaptcha.toBase64());
                request.getSession().setAttribute("captcha", gifCaptcha.text().toLowerCase());
                ResponseUtils.jsonResponse(response,
                        new ApiSimpleResponse<>()
                                .requestId(IdUtil.objectId())
                                .businessMessage("")
                                .message("")
                                .success(true)
                                .code(ApiSimpleResponse.RESPONSE_CODE_SUCCESS)
                                .data(map)
                );
                log.info("gifCaptcha:{}",gifCaptcha.text());
            } else {
                // 使用gif验证码
                GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
                CaptchaUtil.out(gifCaptcha, request, response);
                log.info("gifCaptcha:{}",gifCaptcha.text());
            }

            return;
        } else {
            if (StringUtil.equalsIgnoreCase(requestUrl, securityProperties.getLoginProcessUrl())) {
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
