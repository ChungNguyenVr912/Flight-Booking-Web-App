package dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightCardDTO {
    private String id;
    private String flightCode;
    private String airlinesName;
    private String airlinesImgUrl;
    private String airplaneName;
    private boolean withBusinessClass;
    private String departure;
    private String destination;
    private String departDate;
    private String departTime;
    private LocalDateTime sortDepartTime;
    private String arrivalTime;
    private String flightTime;
    private String basePrice;
    private long sortBasePrice;
}
