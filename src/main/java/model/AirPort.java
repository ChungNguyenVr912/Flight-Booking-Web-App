package model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirPort {
    private long id;
    private String code;
    private String name;
    private int unrealPosition;
}
