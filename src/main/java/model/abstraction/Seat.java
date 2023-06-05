package model.abstraction;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Seat {
    private long id;
    private long airPlaneID;
    private String flightID;
    private String seatCode;
    private boolean booked;
    private double priceMulti;
}
