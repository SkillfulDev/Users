package ua.chernonog.users.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class BirthdateRangeRequest {
    LocalDate from;

    LocalDate to;
}
