package model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Flight {
    private String id;
    private String flightCode;
    private long airLinesID;
    private long airPlaneID;
    private String departure;
    private String destination;
    private LocalDateTime departTime;
    private LocalDateTime arrivalTime;
    private double basePrice;
}
