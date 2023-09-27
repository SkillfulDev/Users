package ua.chernonog.users.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter@Setter
@NoArgsConstructor

public class UserRequest {
    long id;

    String email;

    String firstName;

    String lastName;

    LocalDate birthdate;

    String address;

    String phoneNumber;
}
