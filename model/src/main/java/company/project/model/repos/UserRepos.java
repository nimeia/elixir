package company.project.model.repos;

import company.project.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<UserEntity,Long> {
}
