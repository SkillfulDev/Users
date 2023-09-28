package ua.chernonog.users;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.chernonog.users.entity.UserEntity;
import ua.chernonog.users.mapper.UserCustomMapper;
import ua.chernonog.users.mapper.UserMapper;
import ua.chernonog.users.model.request.BirthdateRangeRequest;
import ua.chernonog.users.model.request.UserRequest;
import ua.chernonog.users.model.response.UserResponse;
import ua.chernonog.users.repository.UserRepository;
import ua.chernonog.users.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserCustomMapper userCustomMapper;


    @Test
    void findAll_should_return_users_list() {
        // Given
        UserEntity userEntity = this.buildTestingUser();
        // When
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        List<UserResponse> users = this.userService.findAllUsers();
        // Then
        assertEquals(1, users.size());
        verify(this.userRepository).findAll();
    }
    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;
    @Test
    void save_should_insert_new_user() {
        // Given
        UserEntity userEntity = this.buildTestingUser();
        // When
        this.userRepository.save(userEntity);
        // Then
        verify(userRepository).save(userEntityCaptor.capture());
        UserEntity capturedUserEntity = userEntityCaptor.getValue();
        log.info("cap={}",capturedUserEntity.toString());
        assertEquals(userEntity.getId(), capturedUserEntity.getId());
        assertEquals(userEntity.getFirstName(), capturedUserEntity.getFirstName());
    }
    @Test
    void deleteById_should_delete_employee() {
        // When
        this.userRepository.deleteById(1L);
        // Then
        verify(this.userRepository).deleteById(1L);
    }
    @Test
    void findAll_should_return_validUsers_list() {
        // Given
        UserEntity userEntity = this.buildTestingUser();
        LocalDate startDate = LocalDate.of(1986, 05, 11);
        LocalDate endDate = LocalDate.of(1989, 05, 13);

        // When
        when(userRepository.findByBirthdateBetween((
                        LocalDate.of(1986, 05, 11)),
                LocalDate.of(1989, 05, 13))).thenReturn(List.of(userEntity));

        List<UserResponse> users = this.userService.findAllValidUser(birthdateRangeRequest());
        log.info("Users={}", users);
        // Then
        assertEquals(1, users.size());
        verify(this.userRepository).findByBirthdateBetween(startDate, endDate);
    }
    private UserEntity buildTestingUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("FIRST_NAME");
        userEntity.setLastName("LAST_NAME");
        userEntity.setBirthdate(LocalDate.of(1987, 05, 13));
        userEntity.setAddress("Kiev");
        userEntity.setPhoneNumber("+38045645");
        return userEntity;
    }

    private UserRequest buildTestingUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setId(1L);
        userRequest.setFirstName("FIRST_NAME");
        userRequest.setLastName("LAST_NAME");
        userRequest.setBirthdate(LocalDate.of(1986, 06, 13));
        userRequest.setAddress("Kiev");
        userRequest.setPhoneNumber("+38045645");
        return userRequest;
    }
    private BirthdateRangeRequest birthdateRangeRequest() {
        return BirthdateRangeRequest.builder().
        from(LocalDate.of(1986, 05, 11))
                .to(LocalDate.of(1989, 05, 13))
                .build();

    }
}
