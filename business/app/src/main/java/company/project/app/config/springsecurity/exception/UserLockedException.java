package company.project.app.config.springsecurity.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 验证码错误时抛出
 *
 * @author huang
 */
public class UserLockedException extends InternalAuthenticationServiceException {

    public UserLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserLockedException(String message) {
        super(message);
    }
}
