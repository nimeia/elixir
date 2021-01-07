package company.project.app.config.springsecurity;

import company.project.app.config.springsecurity.exception.MoreThanOneUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * spring security load user info service
 *
 * @author huang
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //todo 增加用户锁定等业务处理逻辑
        List<CustomUserDetails> customUserDetails = jdbcTemplate
                .query(" select * from user where username = ? ",
                        BeanPropertyRowMapper.newInstance(CustomUserDetails.class)
                        , username);
        if (customUserDetails == null || customUserDetails.size() == 0) {
            logger.info("login fails cant find the user :{}", username);
            throw new UsernameNotFoundException("user not exit!");
        } else {
            if (customUserDetails.size() > 1) {
                logger.info("more than one user whith the name " + username);
                throw new MoreThanOneUserException("more than one user whith the name " + username);
            }
            logger.info("user {} prepare to login ...", customUserDetails.get(0));
            return customUserDetails.get(0);
        }
    }
}
