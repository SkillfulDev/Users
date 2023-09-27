package ua.chernonog.users.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class BirthdateRangeRequest {
    LocalDate from;

    LocalDate to;
}
