package model.impl;

import lombok.*;
import lombok.experimental.SuperBuilder;
import model.abstraction.User;


@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Admin extends User {
}
