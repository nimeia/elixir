package company.project.model.user.repos;

import company.project.model.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsername(String username);
}
