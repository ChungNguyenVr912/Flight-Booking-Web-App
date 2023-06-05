package model.impl;

import lombok.*;
import lombok.experimental.SuperBuilder;
import model.abstraction.User;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Customer extends User {
    private String gender;
    private String phoneNumber;
    private LocalDate dayOfBirth;
}
