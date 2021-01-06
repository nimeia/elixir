package company.project.app.user.mapper;

import company.project.app.user.vo.UserVo;
import company.project.service.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserVoMapper {

    UserVoMapper mapper = Mappers.getMapper(UserVoMapper.class);

    UserDto toUserDto(UserVo userVo);

    UserVo toUserVo(UserDto userDto);

    void mergeToUserDto(UserVo userVo,@MappingTarget UserDto userDto);

}
