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
import ua.chernonog.users.model.response.UserResponse;
import ua.chernonog.users.repository.UserRepository;
import ua.chernonog.users.service.UserService;

import java.time.LocalDate;
import java.util.List;

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
        UserEntity userEntity = this.buildTestingUser();
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        List<UserResponse> users = this.userService.findAllUsers();
        assertEquals(1, users.size());
        verify(this.userRepository).findAll();
    }

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    @Test
    void save_should_insert_new_user() {
        UserEntity userEntity = this.buildTestingUser();
        this.userRepository.save(userEntity);
        verify(userRepository).save(userEntityCaptor.capture());
        UserEntity capturedUserEntity = userEntityCaptor.getValue();
        assertEquals(userEntity.getId(), capturedUserEntity.getId());
        assertEquals(userEntity.getFirstName(), capturedUserEntity.getFirstName());
    }

    @Test
    void deleteById_should_delete_user() {
        this.userRepository.deleteById(1L);
        verify(this.userRepository).deleteById(1L);
    }

    @Test
    void findAll_should_return_validUsers_list() {
        UserEntity userEntity = this.buildTestingUser();
        LocalDate startDate = LocalDate.of(1986, 05, 11);
        LocalDate endDate = LocalDate.of(1989, 05, 13);
        when(userRepository.findByBirthdateBetween((
                        LocalDate.of(1986, 05, 11)),
                LocalDate.of(1989, 05, 13))).thenReturn(List.of(userEntity));
        List<UserResponse> users = this.userService.findAllValidUser(birthdateRangeRequest());
        assertEquals(1, users.size());
        verify(this.userRepository).findByBirthdateBetween(startDate, endDate);
    }

    private UserEntity buildTestingUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Dany");
        userEntity.setLastName("Stronghill");
        userEntity.setBirthdate(LocalDate.of(1987, 05, 13));
        userEntity.setAddress("Morocco");
        userEntity.setPhoneNumber("+25646545646");
        userEntity.setEmail("morocco@rt.rt");
        return userEntity;
    }

    private BirthdateRangeRequest birthdateRangeRequest() {
        return BirthdateRangeRequest.builder().
                from(LocalDate.of(1986, 05, 11))
                .to(LocalDate.of(1989, 05, 13))
                .build();
    }
}
