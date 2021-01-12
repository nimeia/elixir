package company.project.app.config.springsecurity.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 验证码错误时抛出
 *
 * @author huang
 */
public class ValidCodeIncorrectException extends InternalAuthenticationServiceException {
    public ValidCodeIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidCodeIncorrectException(String message) {
        super(message);
    }
}
