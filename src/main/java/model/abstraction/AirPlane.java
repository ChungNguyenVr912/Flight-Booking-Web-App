package model.abstraction;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class AirPlane {
    private long id;
    private String name;
}
