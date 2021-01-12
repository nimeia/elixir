package company.project.core.config.mvc;


import cn.hutool.core.util.IdUtil;
import company.project.api.base.response.ApiSimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    // 设置响应状态码为200
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiSimpleResponse handleBindGetException(Exception e) {
        log.error(e.getMessage(), e);
        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put("timestamp", new Date());
        // 获取所有异常
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ee = (MethodArgumentNotValidException) e;
            List<String> errors = ee.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.toList());
            body.put("error", errors);
        } else if (e instanceof HttpMessageNotReadableException) {
            body.put("error", e.getMessage());
        }

        return new ApiSimpleResponse()
                .code(ApiSimpleResponse.RESPONSE_CODE_ERROR)
                .success(false)
                .message("data format not accept")
                .businessMessage("违反数据约束")
                .data(body)
                .system("app")
                .requestId(IdUtil.objectId());
    }

    @ExceptionHandler(value = {Exception.class})
    // 设置响应状态码为200
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiSimpleResponse commonException(Exception e) {
        log.error(e.getMessage(), e);
        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("error", e.getMessage());

        return new ApiSimpleResponse()
                .code(ApiSimpleResponse.RESPONSE_CODE_ERROR)
                .success(false)
                .message("unknown exception")
                .businessMessage("系统未知错误")
                .data(body)
                .system("app")
                .requestId(IdUtil.objectId());
    }
}