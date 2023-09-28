package ua.chernonog.users.model.request;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter@Setter
@NoArgsConstructor
@Builder
@ToString
public class UserRequest {
    long id;

    String email;

    String firstName;

    String lastName;

    LocalDate birthdate;

    String address;

    String phoneNumber;
}
