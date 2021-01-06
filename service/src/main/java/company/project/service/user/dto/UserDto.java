package company.project.service.user.dto;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto implements Serializable {

    private Long id;

    private String password;

    private String username;

    private Boolean enabled;

    private String displayName;

    private String phone;

    private String email;

    private String createBy;

    private Date createTime;

    private Boolean locked;

    private Date lockTime;

    private Date shortLockTime;

    private Integer loginFailTimes;

    private Date validTime;
}
