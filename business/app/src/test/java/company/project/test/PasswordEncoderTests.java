package company.project.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class PasswordEncoderTests {

    @Test
    public void passwordTest() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .authorities("admin").roles("admin")
                .username("admin").password("admin").build();
        System.out.println(userDetails.getPassword());
        Assert.notNull(userDetails);
    }
}
