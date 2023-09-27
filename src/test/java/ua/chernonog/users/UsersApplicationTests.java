package ua.chernonog.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.chernonog.users.controller.UserController;
import ua.chernonog.users.entity.UserEntity;
import ua.chernonog.users.model.response.UserResponse;
import ua.chernonog.users.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class UsersApplicationTests {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    UserResponse userResponse;

    UserEntity userResponse1 = UserEntity.builder()
            .id(1l)
            .email("chernonog.evgeniy@gmail.com")
            .firstName("Tom")
            .lastName("Chernonog")
            .birthdate(LocalDate.of(1986,05,13))
            .address("Kiev")
            .phoneNumber("546546546")
            .build();
    UserEntity userResponse2 = UserEntity.builder()
            .id(1l)
            .email("chernonog.evgeniy@gmail.com")
            .firstName("Evgeniy")
            .lastName("Chernonog")
            .birthdate(LocalDate.of(1986,05,13))
            .address("Kiev")
            .phoneNumber("546546546")
            .build();
    UserEntity userResponse3 = UserEntity.builder()
            .id(1l)
            .email("chernonog.evgeniy@gmail.com")
            .firstName("Andrew")
            .lastName("Chernonog")
            .birthdate(LocalDate.of(1986,05,13))
            .address("Kiev")
            .phoneNumber("546546546")
            .build();


//    @Before
//    public void setup(){
////        MockitoAnnotations.initMocks(this);
//       mockMvc= MockMvcBuilders.standaloneSetup(userController).build();
//
//    }
//@Before
//public void init() {
//    this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//}
    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    @Test
    public void getAllUser()throws Exception{
        List<UserEntity> users = new ArrayList<>(List.of(userResponse1,userResponse2,userResponse3));
          Mockito.when(userRepository.findAll()).thenReturn(users);
          mockMvc.perform(MockMvcRequestBuilders
                  .get("/allUsers")
                  .contentType(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk())
                  .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                  .andExpect(jsonPath("$[2].firstname",is("Evgeniy")));

    }
}


