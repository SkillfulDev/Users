package ua.chernonog.users.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.chernonog.users.entity.UserEntity;
import ua.chernonog.users.model.request.UserRequest;
import ua.chernonog.users.model.response.UserResponse;

@AllArgsConstructor
@Component
public class UserCustomMapper {



    public UserEntity updateUserFields(UserEntity existingUser, UserRequest userRequest) {
        // Оновлюємо поля користувача, якщо вони не є null у UserRequest
        if (userRequest.getEmail() != null) {
            existingUser.setEmail(userRequest.getEmail());
        }
        if (userRequest.getFirstName() != null) {
            existingUser.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null) {
            existingUser.setLastName(userRequest.getLastName());
        }
        if (userRequest.getBirthdate() != null) {
            existingUser.setBirthdate(userRequest.getBirthdate());
        }
        if (userRequest.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userRequest.getPhoneNumber());
        }
        if (userRequest.getAddress() != null) {
            existingUser.setAddress(userRequest.getAddress());
        }

        return existingUser;
    }
   public UserEntity userRequestToUserEntity(UserRequest userRequest){
       return UserEntity.builder().id(userRequest.getId())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .birthdate(userRequest.getBirthdate())
                .address(userRequest.getAddress())
                .phoneNumber(userRequest.getPhoneNumber())
                .build();
    }
   public UserResponse userEntityToUserResponse(UserEntity userEntity){
        return UserResponse.builder().id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .birthdate(userEntity.getBirthdate())
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .build();
    }
}
