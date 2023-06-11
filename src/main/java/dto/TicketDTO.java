package dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDTO {
    private String flightCode;
    private String airlines;
    private String airlinesImg;
    private String departure;
    private String departureCode;
    private String destination;
    private String destinationCode;
    private String departTime;
    private String arrivalTime;
    private String flyTime;
    private String seatCode;
    private String seatClass;
    private String passengerName;
    private String passengerGender;
}
