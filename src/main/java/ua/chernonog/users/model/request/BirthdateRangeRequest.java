package ua.chernonog.users.model.request;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BirthdateRangeRequest {
    LocalDate from;

    LocalDate to;
}
