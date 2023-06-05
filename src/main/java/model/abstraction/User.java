package model.abstraction;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public abstract class User{
    protected int id;
    protected String userName;
    protected String email;
    protected String passWord;
    protected String fullName;
}
