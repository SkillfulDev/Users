package ua.chernonog.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.chernonog.users.controller.UserController;
import ua.chernonog.users.entity.UserEntity;
import ua.chernonog.users.mapper.UserCustomMapper;
import ua.chernonog.users.mapper.UserMapper;
import ua.chernonog.users.model.request.BirthdateRangeRequest;
import ua.chernonog.users.model.request.UserRequest;
import ua.chernonog.users.model.response.UserResponse;
import ua.chernonog.users.repository.UserRepository;
import ua.chernonog.users.service.UserService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestApiTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int randomServerPort;

    @Test
    void should_return_employee_list() {
        BirthdateRangeRequest birthdateRangeRequest = new BirthdateRangeRequest();
        birthdateRangeRequest.setFrom(LocalDate.of(1990, 1, 1));
        birthdateRangeRequest.setTo(LocalDate.of(2000, 12, 31));
        // Отправляем POST-запрос с объектом BirthdateRangeRequest
        ResponseEntity<UserResponse[]> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + randomServerPort + "/users",
                birthdateRangeRequest,
                UserResponse[].class
        );

        // Проверяем, что получили успешный статус ответа
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Получаем тело ответа
        UserResponse[] users = responseEntity.getBody();

        // Проверяем, что длина массива пользователей равна ожидаемой (в данном случае 4)
        assertEquals(2, users.length);

        // Проверяем, что имя первого пользователя соответствует ожидаемому имени ("Evgeniy")
        assertEquals("Evgeniy", users[0].getFirstName());
    }

    @Test
    void should_add_new_user() throws Exception {
        UserRequest newUserRequest = new UserRequest();
        newUserRequest.setId(1L);
        newUserRequest.setFirstName("FIRST_NAME");
        newUserRequest.setLastName("LAST_NAME");
        newUserRequest.setBirthdate(LocalDate.of(1986, 06, 13));
        newUserRequest.setAddress("Kiev");
        newUserRequest.setPhoneNumber("+38045645");
        newUserRequest.setEmail("dasffa@gmail.com");

        ResponseEntity<UserResponse> response = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/users/register", newUserRequest, UserResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("FIRST_NAME", response.getBody()
                .getFirstName());
        assertEquals("LAST_NAME", response.getBody()
                .getLastName());
    }

    @Test
    void should_update_existing_employee() throws Exception {
        UserRequest updatedUserRequest = new UserRequest();
        updatedUserRequest.setId(1L);
        updatedUserRequest.setFirstName("newUser");
//        updatedUserRequest.setLastName("LAST_NAME");
//        updatedUserRequest.setBirthdate(LocalDate.of(1986, 06, 13));
//        updatedUserRequest.setAddress("Kiev");
//        updatedUserRequest.setPhoneNumber("+38045645");
//        updatedUserRequest.setEmail("dasffa@gmail.com");
        // Используем метод PathVariable() для передачи значения переменной {id} в URL-адрес
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", "2");
        HttpEntity requestUpdate = new HttpEntity<>(updatedUserRequest);
        ResponseEntity<UserResponse> response = restTemplate.exchange("http://localhost:" + randomServerPort
                + "/users/update/{id}", HttpMethod.PUT, requestUpdate, UserResponse.class, pathVariables);

        assertEquals("newUser", response.getBody()
                .getFirstName());
    }

    @Test
    void should_remove_employee() throws Exception {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", "2");

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + randomServerPort + "/users/delete/{id}",
                HttpMethod.DELETE, null, String.class, pathVariables);

        assertEquals("User deleted successfully", response.getBody());
    }
}

