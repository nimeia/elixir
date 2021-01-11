package company.project.app.config.springsecurity;

import lombok.*;

import java.io.Serializable;

/**
 * 权限定义
 *
 * @author huang
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CustomMetaDataSource implements Serializable {

    private Long id;

    private String name;

    private Boolean showAlways;

    private Integer level;

    private Integer type;

    private String title;

    private String path;

    private String component;

    private String icon;

    private String buttonType;

}
