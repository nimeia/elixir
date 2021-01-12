package company.project.service.user.mapper;

import company.project.model.user.entity.UserEntity;
import company.project.service.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Mapper
public interface UserDtoMapper {

    UserDtoMapper mapper = Mappers.getMapper(UserDtoMapper.class);

    UserDto toUserDto(UserEntity userEntity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity toUserEntity(UserDto userDto);

    /**
     * 新建对象
     * @param userDto
     * @return
     */
    UserEntity initUserEntity(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    void mergeToUserEntity(UserDto userDto, @MappingTarget UserEntity userEntity);
}
