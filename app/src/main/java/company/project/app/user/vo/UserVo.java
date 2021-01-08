package company.project.app.user.vo;

import company.project.app.config.validate.annotation.IdCard;
import company.project.app.config.validate.usergroup.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * user vo
 *
 * @author huang
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserVo implements Serializable {

    @NotNull(groups = {UserUpdate.class, UserQuery.class}, message = "用户ID不能为空")
    @Null(groups = {UserInsert.class}, message = "新建用户不能指定ID")
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户长度必须在2~20之间")
    private String username;

    @NotNull(message = "用户状态不能为空")
    @Null(groups = {UserUpdate.class}, message = "用户状态不能变更")
    private Boolean enabled;

    @NotBlank(message = "用户昵称不能为空")
    @Length(min = 2, max = 20, message = "用户昵称长度必须在2~100之间")
    private String displayName;

    @NotBlank(groups = {UserPhoneUpdate.class}, message = "用户电话不能为空")
    @Null(groups = {UserUpdate.class}, message = "用户电话不能变更")
    @Pattern(regexp = "\\d{11}",message = "电话号格式不正常")
    private String phone;

    @Email
    @NotBlank(groups = {UserEmailUpdate.class}, message = "用户邮件不能为空")
    @Null(groups = {UserUpdate.class}, message = "用户邮件不能变更")
    @Length(min = 2, max = 20, message = "用户邮件长度必须在2~100之间")
    private String email;

    @IdCard(message = "身份证格式不正确")
    private String idCard;
}
