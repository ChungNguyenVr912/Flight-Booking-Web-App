package model.impl;

import lombok.*;
import lombok.experimental.SuperBuilder;
import model.abstraction.User;


@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Airlines extends User {
    private double priceMulti;
}
