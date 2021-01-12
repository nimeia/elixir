package company.project.app.config.springsecurity.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 查出多于一个正常用户时抛出
 *
 * @author huang
 */
public class MoreThanOneUserException extends InternalAuthenticationServiceException {

    public MoreThanOneUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoreThanOneUserException(String message) {
        super(message);
    }
}
