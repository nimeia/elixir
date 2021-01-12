package company.project.core.utils.web;

import company.project.core.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * response helper
 *
 * @author huang
 */
public abstract class ResponseUtils {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * return json response with utf-8 encoding
     * @param response
     * @param message
     */
    public static void jsonResponse(HttpServletResponse response,Object message){
        // POST请求返回json
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        try {
            response.getWriter().write(JsonUtils.toJson(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
