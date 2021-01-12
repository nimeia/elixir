package company.project.core.config.springsecurity.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 被限制登录时抛出
 *
 * @author huang
 */
public class LoginLimitException extends InternalAuthenticationServiceException {
    public LoginLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginLimitException(String message) {
        super(message);
    }
}
