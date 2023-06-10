package dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatDTO {
    private long id;
    private String airplaneName;
    private String flightID;
    private String seatCode;
    private boolean booked;
    private double price;
    private String type;
}
