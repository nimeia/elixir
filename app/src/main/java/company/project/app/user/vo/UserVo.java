package company.project.app.user.vo;

import lombok.*;

import java.io.Serializable;

/**
 * user vo
 * @author huang
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserVo implements Serializable {
    private Long id;

    private String username;

    private Boolean enabled;

    private String displayName;

    private String phone;

    private String email;

}
