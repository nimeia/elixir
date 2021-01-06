package company.project.model.user.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 *
 * @author huang
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    private Date showLockTime;

    private Date shortLockTime;

    private Integer loginFailTimes;

    private Date validTime;

}
