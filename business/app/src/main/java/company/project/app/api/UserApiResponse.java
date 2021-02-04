package company.project.app.api;

import company.project.app.user.validate.annotation.IdCard;
import company.project.app.user.validate.usergroup.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@ToString
public class UserApiResponse {

    private Long id;

    private String username;

    private Boolean enabled;

    private String displayName;

    private String phone;

    private String email;

    private String idCard;
}
