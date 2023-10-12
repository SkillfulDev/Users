package ua.chernonog.users;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.chernonog.users.config.TestDatabaseConfig;
import ua.chernonog.users.controller.UserController;
import ua.chernonog.users.model.response.UserResponse;
import ua.chernonog.users.repository.UserRepository;
import ua.chernonog.users.service.UserService;

import java.util.ArrayList;
import java.util.List;
//@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)

public class TestMvc {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;
    @MockBean
    private UserRepository repository;


    @Test
    public void greetingShouldReturnListOfUsers() throws Exception {
        // Выполните GET-запрос к /greeting и убедитесь, что возвращенный JSON соответствует ожидаемым данным.
        mockMvc.perform(get("/allUsers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"email\":\"Kate@gmail.com\",\"firstName\":\"Kate\",\"lastName\":\"Winston\",\"birthdate\":\"1999-05-18\",\"address\":\"Kiev\",\"phoneNumber\":\"+380939151678\"}]"));
    }
}
