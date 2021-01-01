package company.project.model;

import lombok.*;

import java.io.Serializable;

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
public class User implements Serializable {

    private Long id;

    private String name;

    private String displayName;

    private String phone;

    private String email;

}
