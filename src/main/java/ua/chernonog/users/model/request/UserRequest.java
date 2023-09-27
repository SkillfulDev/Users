package ua.chernonog.users.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter@Setter

public class UserRequest {
    long id;

    String email;

    String firstName;

    String lastName;

    LocalDate birthdate;

    String address;

    String phoneNumber;
}
