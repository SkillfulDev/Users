package ua.chernonog.users.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ua.chernonog.users.entity.UserEntity;
import ua.chernonog.users.mapper.UserCustomMapper;
import ua.chernonog.users.mapper.UserMapper;
import ua.chernonog.users.model.request.BirthdateRangeRequest;
import ua.chernonog.users.model.request.UserRequest;
import ua.chernonog.users.model.response.UserResponse;
import ua.chernonog.users.repository.UserRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
@NoArgsConstructor
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    UserCustomMapper userCustomMapper;

    public UserResponse saveUser(UserRequest userRequest) {
        if (!IsNotLessThan18Age(userRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            UserEntity userEntity = userRepository.save(UserEntity.builder()
                    .email(userRequest.getEmail())
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .address(userRequest.getAddress())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .birthdate(userRequest.getBirthdate())
                    .build());
            userMapper.userEntityToUserResponse(userEntity);
            return userMapper.userEntityToUserResponse(userEntity);
        }
    }

    private boolean IsNotLessThan18Age(UserRequest userRequest) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthdate = userRequest.getBirthdate();

        // Визначаємо різницю між поточною датою та датою народження
        Period age = Period.between(birthdate, currentDate);

        return age.getYears() >= 18;
    }

    public UserResponse updateUser(long id, UserRequest userRequest) {

        UserEntity foundedUser = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UserEntity updatedUser = userRepository.save(userCustomMapper.updateUserFields(foundedUser, userRequest));
        return userMapper.userEntityToUserResponse(updatedUser);

    }

    public String deleteUser(long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)));


        return "User deleted successful";
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAllValidUser(BirthdateRangeRequest birthdateRangeRequest) {
        List<UserEntity> foundedUsers = userRepository.findByBirthdateBetween(birthdateRangeRequest.getFrom(), birthdateRangeRequest.getTo());
        return foundedUsers.stream().map(userEntity ->
                userMapper.userEntityToUserResponse(userEntity)).toList();

    }

    public List<UserResponse> findAllUsers() {
      return userRepository.findAll().stream().map(userMapper::userEntityToUserResponse).collect(Collectors.toList());
    }
}
