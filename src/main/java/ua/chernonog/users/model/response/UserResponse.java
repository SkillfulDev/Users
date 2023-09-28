package ua.chernonog.users.model.response;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@ToString

public class UserResponse {
    long id;
    String email;
    String firstName;

    String lastName;

    LocalDate birthdate;

    String address;

    String phoneNumber;
}
