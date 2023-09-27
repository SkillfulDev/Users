package ua.chernonog.users.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserResponse {
    long id;
    String email;
    String firstName;

    String lastName;

    LocalDate birthdate;

    String address;

    String phoneNumber;
}
