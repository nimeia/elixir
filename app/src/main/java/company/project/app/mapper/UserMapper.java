package company.project.app.mapper;

import company.project.app.vo.User;
import company.project.app.vo.UserDTO;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

//    @Mapping()
    UserDTO toUserDTO(User user);
}
