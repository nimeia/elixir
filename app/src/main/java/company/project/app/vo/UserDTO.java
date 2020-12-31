package company.project.app.vo;

import java.io.Serializable;

/**
 * userDTO
 *
 * @author huang
 *
 */
public class UserDTO implements Serializable {

    private Long id;

    private String name;

    private String age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
