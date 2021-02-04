package company.project.app.api;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class UserApiRequest {

    private String username;

    private Boolean enabled;

    private String displayName;

    private String phone;

    private String email;

    private String idCard;
}
