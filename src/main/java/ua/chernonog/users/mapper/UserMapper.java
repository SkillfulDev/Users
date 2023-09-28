package ua.chernonog.users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ua.chernonog.users.entity.UserEntity;
import ua.chernonog.users.model.request.UserRequest;
import ua.chernonog.users.model.response.UserResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface UserMapper {

    UserResponse userEntityToUserResponse(UserEntity userEntity);

//    UserEntity userRequestToUserEntity(UserRequest userRequest);


}
