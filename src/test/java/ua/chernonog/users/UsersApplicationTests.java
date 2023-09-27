package ua.chernonog.users;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.chernonog.users.controller.UserController;
import ua.chernonog.users.entity.UserEntity;
import ua.chernonog.users.mapper.UserCustomMapper;
import ua.chernonog.users.mapper.UserMapper;
import ua.chernonog.users.model.request.UserRequest;
import ua.chernonog.users.model.response.UserResponse;
import ua.chernonog.users.repository.UserRepository;
import ua.chernonog.users.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
class UsersApplicationTests {

    @Mock
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserCustomMapper userCustomMapper;

    private UserService userService;

    private static UserEntity testUserUserEntity;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeClass
    public static void prepareTestData() {
        testUserUserEntity =UserEntity
                .builder()
                .id(123L)
                .firstName("Evgeniy")
                .lastName("Chernonog")
                .email("chernonog.evheniy@gmail.com")
                .address("some")
                .phoneNumber("dsada")
                .build();
    }

    @Before
    public void init() {
        userService = new UserService(userRepository,userMapper,userCustomMapper);
    }
    @Test
    public void updateTest() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(testUserUserEntity));
        when(userRepository.save(any(UserEntity.class))).then(returnsFirstArg());
        UserEntity UserEntityForUpdate = UserEntity
                .builder()
                .id(123L)
                .firstName("Evgeniy")
                .lastName("Chernonog")
                .email("chernonog.evheniy@gmail.com")
                .address("some")
                .phoneNumber("dsada")
                .build();

       UserResponse userResponse1 = userService.saveUser(userRequest);

        assertNotNull(userResponse1);
        assertSame(userResponse1.getId(),testUserUserEntity.getId());
//        assertThat(userResponse1.get).isEqualTo(robotForUpdate.getName());
        assertTrue(userResponse1.getFirstName().equals(UserEntityForUpdate.getFirstName()));

    }
}


