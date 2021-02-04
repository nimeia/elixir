package company.project.app.api;

import com.github.lianjiatech.retrofit.spring.boot.annotation.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import retrofit2.http.Body;
import retrofit2.http.POST;

@RetrofitClient(baseUrl = "${api.user.url}")
@Intercept(handler = LoginInterceper.class , include = {"/user/**"})
public interface UserApi {

    @POST("user/addUser")
    UserApiResponse addUser(@Body UserApiRequest userApiRequest);
}
