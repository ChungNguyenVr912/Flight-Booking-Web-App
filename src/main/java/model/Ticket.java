package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    private String id;
    private long userID;
    private String flightID;
    private long seatID;
    private String passengerName;
    private String passengerID;
    private String passengerGender;
    private String contact;
    private String status;
}
