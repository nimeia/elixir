package company.project.app.api;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import company.project.api.base.response.ApiSimpleResponse;
import company.project.core.utils.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 演示登录调接口时需要登录的场景
 *
 */
@Slf4j
@Component
public class LoginInterceper extends BasePathMatchInterceptor {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.user.username}")
    private String username;
    @Value("${api.user.password}")
    private String password;
    @Value("${api.user.loginurl}")
    private String loginurl;

    private String token;

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        if (token == null || token.equals("")) {
            doLogin();
        }

        Request request = chain.request();
        Request newRequest = request.newBuilder().addHeader("X-Auth-Token", token).build();
        Response response = chain.proceed(newRequest);

        if (response.isSuccessful()) {
            String responseTxt = response.body().string();
            ApiSimpleResponse apiSimpleResponse = JsonUtils.toObject(responseTxt, ApiSimpleResponse.class);
            if(!apiSimpleResponse.getSuccess()
                    && ApiSimpleResponse.RESPONSE_LOGIN_NEED.equals(apiSimpleResponse.getCode()){
                token = null;
                doLogin();
            }
        }
        return response;
    }

    /**
     * 调用时自动登录
     */
    private void doLogin(){
        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);

        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);

        Map loginTokenMap = restTemplate.postForObject(loginurl, request, HashMap.class);
        if (Boolean.valueOf(loginTokenMap.get("success").toString())) {
            token = ((Map) loginTokenMap.get("data")).get("X-Auth-Token").toString();
        }
    }
}
