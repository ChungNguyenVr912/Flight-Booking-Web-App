<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.google.zxing.BarcodeFormat" %>
<%@ page import="com.google.zxing.EncodeHintType" %>
<%@ page import="com.google.zxing.WriterException" %>
<%@ page import="com.google.zxing.common.BitMatrix" %>
<%@ page import="com.google.zxing.qrcode.QRCodeWriter" %>
<%@ page import="com.google.zxing.qrcode.decoder.ErrorCorrectionLevel" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="dto.TicketDTO" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Flight Booking</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/d625a478fd.js" crossorigin="anonymous"></script>
</head>
<body>
<%@include file="./WEB-INF/view/header.jsp" %>
<div class="position-fixed sticky-top" style="z-index: -1; width: 100%">
    <img src="https://cdn.discordapp.com/attachments/646934670679998474/1117000053555466331/universe-space-stars-digital-art-wallpaper-7bb61c6d53e13f7995f4fba93d5c0c70.jpg"
         alt="background" style="width: 100%">
</div>


<div class="main-content d-flex container-fluid mt-4">
    <div class="col-8 d-flex justify-content-center bg-light bg-opacity-75 p-4 align-items-center" style="height: 501px">
        <jsp:useBean id="ticketDTO" scope="request" type="dto.TicketDTO"/>
        <div class="position-absolute" style="opacity: 20%">
            <img src="<c:out value="${ticketDTO.airlinesImg}"></c:out>" alt="logo">
        </div>
        <div class="ticket-content d-flex flex-column position-relative" style="z-index: 2; width: 90%">
            <div class="border-bottom mb-4 pt-4 position-relative">
                <h3>Your Ticket</h3>
                <div class="position-absolute top-0 end-0" >
                    <c:if test="${ticketDTO.seatClass.equals('business')}">
                        <img src="https://media.discordapp.net/attachments/646934670679998474/1115522268374769724/business-removebg-preview.png?width=861&height=335"
                             alt="class" style="height: 5em">
                    </c:if>
                    <c:if test="${!ticketDTO.seatClass.equals('business')}">
                        <img src="https://media.discordapp.net/attachments/646934670679998474/1115522268114731039/economy-removebg-preview.png?width=862&height=337"
                             alt="class" style="height: 5em">
                    </c:if>
                </div>
            </div>
            <div class="d-flex justify-content-between">
                <div class="fw-bold fs-5">Passenger:</div>
                <div>
                    <c:if test="${ticketDTO.passengerGender.equals('Male')}">
                        Mr: <c:out value="${ticketDTO.passengerName}"/>
                    </c:if>
                    <c:if test="${!ticketDTO.passengerGender.equals('Male')}">
                        Mrs/Miss: <c:out value="${ticketDTO.passengerName}"/>
                    </c:if>
                </div>
            </div>
            <div class="d-flex justify-content-between">
                <div class="fw-bold fs-5">Seat: </div>
                <div class="text-warning fw-bold fs-4"><c:out value="${ticketDTO.seatCode}"/></div>
            </div>
            <div class="d-flex justify-content-between">
                <div class="fw-bold fs-5">Flight:</div>
                <div class="fw-bold text-primary"><c:out value="${ticketDTO.flightCode}"/></div>
            </div>
            <div class="d-flex justify-content-between">
                <div class="fw-bold fs-5">Fly route:</div>
                <div class="col-8 ms-5 d-flex justify-content-between">
                    <span><c:out value="${ticketDTO.departure}"/></span>
                    <span><c:out value="${ticketDTO.destination}"/></span>
                </div>
            </div>
            <div class="d-flex justify-content-between">
                <div class="fw-bold fs-5">Time: </div>
                <div class="col-8 ms-5 d-flex justify-content-between">
                    <span><c:out value="${ticketDTO.departureCode}"/>-<c:out value="${ticketDTO.departTime}"/></span>
                    <span><c:out value="${ticketDTO.flyTime}"/></span>
                    <span><c:out value="${ticketDTO.arrivalTime}"/>-<c:out value="${ticketDTO.destinationCode}"/></span>
                </div>
            </div>
        </div>
    </div>
    <div class="col-4">
        <%
//            TicketDTO ticketDTO = (TicketDTO) request.getAttribute("ticketDTO");
            String prefixCall = ticketDTO.getPassengerGender().equals("Male") ? "Mr: " : "Mrs/miss: ";
            String qrData = new StringBuilder().append("Flight: ").append(ticketDTO.getFlightCode())
                    .append("\n").append("Provided by: ").append(ticketDTO.getAirlines()).append("\n")
                    .append("From: ").append(ticketDTO.getDepartureCode()).append("\t To: ")
                    .append(ticketDTO.getDestinationCode()).append("\n").append("Depart: ")
                    .append(ticketDTO.getDepartTime()).append("\t Arrival: ").append(ticketDTO.getArrivalTime())
                    .append(" (").append(ticketDTO.getFlyTime()).append(")\n").append("Seat: ")
                    .append(ticketDTO.getSeatCode()).append("\t").append(ticketDTO.getSeatClass())
                    .append("\n").append("Passenger: ").append(prefixCall).append(ticketDTO.getPassengerName()).toString();

            int qrCodeSize = 250;
            String qrCodeFormat = "UTF-8";
            int qrCodeMargin = 1;
            ErrorCorrectionLevel qrCodeErrorCorrection = ErrorCorrectionLevel.L;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            HashMap<EncodeHintType, ErrorCorrectionLevel> map = new HashMap<>();
            map.put(EncodeHintType.ERROR_CORRECTION, qrCodeErrorCorrection);
            BitMatrix bitMatrix;
            try {
                bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize,
                        map);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
            int matrixWidth = bitMatrix.getWidth();
            out.println("<table style='border: 1px solid black;'>");
            for (int i = 0; i < matrixWidth; i++) {
                out.println("<tr>");
                for (int j = 0; j < matrixWidth; j++) {
                    boolean isBlack = bitMatrix.get(i, j);
                    String cellStyle = isBlack ? "background-color: black;" : "background-color: white;";
                    out.println("<td style='width: 2px; height: 2px; " + cellStyle + "'></td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
        %>
    </div>
</div>
<%@include file="./WEB-INF/view/footer.jsp" %>
</body>
</html>
