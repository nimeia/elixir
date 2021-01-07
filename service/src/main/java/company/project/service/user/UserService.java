package company.project.service.user;

import company.project.model.user.entity.UserEntity;
import company.project.model.user.repos.UserRepos;
import company.project.service.user.dto.UserDto;
import company.project.service.user.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    UserRepos userRepos;

    /**
     * 查找用户
     * @param username
     * @return
     */
    @Cacheable("userDto")
    public UserDto findUser(String username) {
        UserEntity userEntity = userRepos.findByUsername(username);
        UserDto userDto = UserDtoMapper.mapper.toUserDto(userEntity);
        return userDto;
    }

    /**
     * 更新用户
     * @param userDto
     * @return
     */
    @Transactional(readOnly = false)
    public UserDto updateUser(UserDto userDto) {
        UserEntity userEntity = userRepos.getOne(userDto.getId());
        UserDtoMapper.mapper.mergeToUserEntity(userDto, userEntity);
        userRepos.save(userEntity);
        return UserDtoMapper.mapper.toUserDto(userEntity);
    }

    /**
     * 新增用户
     * @param userDto
     * @return
     */
    @Transactional
    public UserDto insertUser(UserDto userDto){
        UserEntity userEntity = UserDtoMapper.mapper.toUserEntity(userDto);
        userRepos.save(userEntity);
        return UserDtoMapper.mapper.toUserDto(userEntity);
    }
}
