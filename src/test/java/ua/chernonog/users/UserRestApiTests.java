package ua.chernonog.users;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ua.chernonog.users.model.request.BirthdateRangeRequest;
import ua.chernonog.users.model.request.UserRequest;
import ua.chernonog.users.model.response.UserResponse;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestApiTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int randomServerPort;

    @Test
    void should_return_users_list() {
        BirthdateRangeRequest birthdateRangeRequest = new BirthdateRangeRequest();
        birthdateRangeRequest.setFrom(LocalDate.of(1990, 1, 1));
        birthdateRangeRequest.setTo(LocalDate.of(2000, 12, 31));
        ResponseEntity<UserResponse[]> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + randomServerPort + "/users",
                birthdateRangeRequest,
                UserResponse[].class
        );
        assertEquals(200, responseEntity.getStatusCodeValue());
        UserResponse[] users = responseEntity.getBody();
        assertEquals(2, users.length);
        assertEquals("Kate", users[0].getFirstName());
    }

    @Test
    void should_add_new_user() throws Exception {
        UserRequest newUserRequest = new UserRequest();
        newUserRequest.setId(1L);
        newUserRequest.setFirstName("Paulo");
        newUserRequest.setLastName("Fernando");
        newUserRequest.setBirthdate(LocalDate.of(1988, 06, 21));
        newUserRequest.setAddress("Mexico");
        newUserRequest.setPhoneNumber("+5412399613");
        newUserRequest.setEmail("fernando@bing.com");

        ResponseEntity<UserResponse> response = restTemplate.postForEntity("http://localhost:" + randomServerPort
                + "/users/register", newUserRequest, UserResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Paulo", response.getBody()
                .getFirstName());
        assertEquals("Fernando", response.getBody()
                .getLastName());
        assertEquals(LocalDate.of(1988, 06, 21), response.getBody()
                .getBirthdate());
        assertEquals("Mexico", response.getBody()
                .getAddress());
        assertEquals("+5412399613", response.getBody()
                .getPhoneNumber());
        assertEquals("fernando@bing.com", response.getBody()
                .getEmail());
    }

    @Test
    void should_update_existing_user() throws Exception {
        UserRequest updatedUserRequest = new UserRequest();
        updatedUserRequest.setId(1L);
        updatedUserRequest.setFirstName("Jane");
        updatedUserRequest.setLastName("Soul");
//        updatedUserRequest.setBirthdate(LocalDate.of(2001, 07, 22));
//        updatedUserRequest.setAddress("Kiev");
//        updatedUserRequest.setPhoneNumber("+15645665163");
//        updatedUserRequest.setEmail("Jane_soul@gmail.com");
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", "2");
        HttpEntity requestUpdate = new HttpEntity<>(updatedUserRequest);
        ResponseEntity<UserResponse> response = restTemplate.exchange("http://localhost:" + randomServerPort
                + "/users/update/{id}", HttpMethod.PUT, requestUpdate, UserResponse.class, pathVariables);

        assertEquals("Jane", response.getBody()
                .getFirstName());
        assertEquals("Soul", response.getBody()
                .getLastName());
//        assertEquals(LocalDate.of(2001, 07, 22), response.getBody()
//                .getBirthdate());
//        assertEquals("Kiev", response.getBody()
//                .getAddress());
//        assertEquals("+15645665163", response.getBody()
//                .getPhoneNumber());
//        assertEquals("Jane_soul@gmail.com", response.getBody()
//                .getEmail());
    }

    @Test
    void should_remove_user() throws Exception {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", "2");
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + randomServerPort + "/users/delete/{id}",
                HttpMethod.DELETE, null, String.class, pathVariables);
        assertEquals("User deleted successfully", response.getBody());
    }
}

