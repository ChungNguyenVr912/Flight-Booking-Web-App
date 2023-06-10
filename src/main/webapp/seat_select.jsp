<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/d625a478fd.js" crossorigin="anonymous"></script>
    <style>
        .lug-aside {
            z-index: 3;
            border-radius: 10px;
            top: inherit;
            height: calc(100vh - 165px);
            overflow: auto;
        }

        .flight-info {
            color: #004677;
        }
    </style>
</head>
<body>
<%@include file="./WEB-INF/view/header.jsp" %>

<div class="main-content mt-2">
    <form method="post" action="">
        <jsp:useBean id="flightDTO" scope="request" type="dto.FlightCardDTO"/>
        <jsp:useBean id="airportName" scope="request" type="java.util.List"/>
        <jsp:useBean id="seatDtoList" scope="request" type="java.util.List<java.util.List<dto.SeatDTO>>"/>
        <input type="hidden" id="basePrice" value="${flightDTO.sortBasePrice}">
        <input type="hidden" id="luggagePrice" value="0">
        <input type="hidden" id="seatPriceDifference" value="0">
        <div class="d-flex container-fluid">
            <aside class="lug-aside bd-sidebar col-3 position-fixed sticky-top bg-light min-vh-100 bg-opacity-75"
                   style="left: calc(var(--bs-gutter-x) * .5)">
                <div class="select-luggage container-fluid d-flex justify-content-center border-bottom border-warning">
                    <img src="<c:out value='${flightDTO.airlinesImgUrl}'></c:out>"
                         alt="logo" style="width: 75%">
                </div>
                <div class="select-luggage container-fluid p-3">
                    <div class="d-flex justify-content-between mb-3">
                        <div class="fw-bold fs-5">Flight:</div>
                        <div class="text-right flight-info"><c:out value="${flightDTO.flightCode}"></c:out></div>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <div class="col fw-bold"><c:out value="${airportName.get(0)}"></c:out></div>
                        <div class="col-auto mt-2 ms-1 me-1 d-flex"><i
                                class="fa-solid fa-xl fa-shuttle-space fa-beat-fade"
                                style="color: #2d1600;"></i></div>
                        <div class="col fw-bold d-flex justify-content-end"><c:out
                                value="${airportName.get(1)}"></c:out></div>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <div class="fw-bold">Depart:</div>
                        <div class="text-right flight-info"><c:out value="${flightDTO.departTime}"></c:out></div>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <div class="fw-bold">Arrival:</div>
                        <div class="text-right flight-info"><c:out value="${flightDTO.arrivalTime}"></c:out></div>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <div class="fw-bold">Fly time:</div>
                        <div class="text-right flight-info"><c:out value="${flightDTO.flightTime}"></c:out></div>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <div class="fw-bold">Provided by:</div>
                        <div class="text-right flight-info "><c:out value="${flightDTO.airlinesName}"></c:out></div>
                    </div>
                </div>
            </aside>
            <div class="col-3"></div>
            <div class="col-9 d-flex justify-content-evenly bg-opacity-50 ms-2 bg-dark" style="border-radius: 10px">
                <div class="d-flex justify-content-center">
                    <div class="col-auto">
                        <img src="https://cdn.discordapp.com/attachments/646934670679998474/1116579264469413888/Each-Plane-seatmap-472x1445-removebg-preview.png"
                             alt="">
                    </div>
                    <button class="btn position-absolute text-bg-info" disabled style="top: inherit; margin-top: 170px">
                        <c:out value="${flightDTO.airplaneName}"></c:out>
                    </button>
                    <div style="z-index: 1; top: 420px" class="position-absolute">
                        <c:forEach var="list" items="${seatDtoList}">
                            <div class="d-flex">
                                <c:forEach var="seatDTO" items="${list}">
                                    <c:if test="${seatDTO.booked == false}">
                                        <div class="col d-flex justify-content-center text-center">
                                            <input type="hidden" value="${seatDTO.price}" id="${seatDTO.seatCode}price">
                                            <input type="radio" class="btn-check input${seatDTO.type}"
                                                   onchange="changeSeatImg(this)"
                                                   id="${seatDTO.seatCode}-${seatDTO.type}"
                                                   name="selectedSeat" value="${seatDTO.id}" hidden="hidden">
                                            <label for="${seatDTO.seatCode}-${seatDTO.type}" class="btn p-0 position-relative">
                                                <span class="${seatDTO.type}" id="label${seatDTO.seatCode}-${seatDTO.type}">
                                                    <c:if test="${seatDTO.type == 'economy'}">
                                                        <img width="100px" height="100px"
                                                             src="https://media.discordapp.net/attachments/646934670679998474/1116256249974108240/seat_eco-removebg-preview.png?width=625&height=625"
                                                             alt="seat_eco">
                                                    </c:if>
                                                    <c:if test="${seatDTO.type == 'business'}">
                                                        <img width="100px" height="100px"
                                                             src="https://media.discordapp.net/attachments/646934670679998474/1116256250217369630/seat_busi-removebg-preview.png?width=625&height=625"
                                                             alt="seat_bus">
                                                    </c:if>
                                                </span>
                                                <span class="position-absolute" style="top: 6px; left: 40%">
                                                    <c:out value="${seatDTO.seatCode}"></c:out>
                                                </span>
                                            </label>
                                        </div>
                                    </c:if>
                                    <c:if test="${seatDTO.booked == true}">
                                        <div class="col d-flex justify-content-center text-center disabled">
                                            <input type="radio" class="btn-check" id="${seatDTO.seatCode}"
                                                   name="selectedSeat" value="${seatDTO.id}" hidden="hidden" disabled>
                                            <label for="${seatDTO.id}" class="btn p-0 position-relative disabled">
                                <span class="${seatDTO.type}" id="label${seatDTO.id}">
                                    <img width="100px" height="100px"
                                         src="https://media.discordapp.net/attachments/646934670679998474/1116256249726648412/seat_booked-removebg-preview.png?width=625&height=625"
                                         alt="seat_booked">
                                </span>
                                                <span class="position-absolute" style="top: 6px; left: 40%">
                                        <c:out value="${seatDTO.seatCode}"></c:out></span>
                                            </label>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:forEach>

                    </div>
                </div>
                <div class="col-4">
                    <aside class="bd-sidebar col-3 position-fixed text-bg-dark lug-aside"
                           style="right: calc(var(--bs-gutter-x) * .5);">
                        <div class="container-fluid d-flex justify-content-center flex-column">
                            <span class="form-label text-center mt-5"><h3>Buy additional luggage</h3></span>
                            <select class="form-select mb-5" name="luggageID" onchange="addLuggage(this.value,${se})">
                                <option value="1" selected>No thanks</option>
                                <option value="2">10kg &nbsp;&nbsp;150.000 VND</option>
                                <option value="3">15kg &nbsp;&nbsp;200.000 VND</option>
                                <option value="4">20kg &nbsp;&nbsp;250.000 VND</option>
                                <option value="5">30kg &nbsp;&nbsp;350.000 VND</option>
                            </select>
                            <div class="mt-5 pt-5 border-top">
                                <div class="d-flex justify-content-between">
                                    <span>Seat selected:</span>
                                    <span class="text-warning" id="displaySeatCode"></span>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <span>Class:</span>
                                    <span id="displaySeatClass"></span>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <span>Luggage:</span>
                                    <span id="displayLuggage"></span>
                                </div>
                            </div>
                            <div class="d-flex flex-column justify-content-center mt-5 mb-3">
                                <input class="text-center mb-3 fw-bold fs-3" id="currentPrice" disabled
                                       value="<c:out value="${flightDTO.basePrice}"></c:out>">
                                <button type="submit" style="width: 100%; color: white"
                                        class="btn btn-warning btn-outline-danger mt-2 fw-bold fs-5">Confirm your choice
                                </button>
                            </div>
                        </div>
                    </aside>
                </div>
            </div>
        </div>
    </form>
</div>

<%@include file="./WEB-INF/view/footer.jsp" %>
<%@include file="./WEB-INF/view/modal.jsp" %>
<script>
    <%@include file="/static/js/account.js" %>
</script>
<script>
    <%@include file="/static/js/seat_select.js" %>
</script>
</body>
</html>