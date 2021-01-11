package company.project.app.user.vo;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UpdatePasswordVo implements Serializable {

    @NotBlank(message = "{update.password.validCode}")
    private String validCode;

    @NotBlank(message = "{update.password.password}")
    private String password;

    @NotBlank(message = "{update.password.repeatPassword}")
    private String repeatPassword;

    @NotBlank(message = "{update.password.username}")
    private String username;

    @NotBlank(message = "{update.password.oldPassword}")
    private String oldPassword;
}
