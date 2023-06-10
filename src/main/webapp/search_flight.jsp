<%@ page import="model.abstraction.User" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.FlightCardDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Isekai Flight Booking</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
          rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<%@include file="./WEB-INF/view/header.jsp" %>

<div class="main-content d-flex container-fluid ps-1 mt-2">
    <aside class="bd-sidebar col-4 position-fixed sticky-top" style="top: inherit">
        <div class="container-fluid" id="select-location">
            <form action="flight" method="post" class="form-control">
                <input type="hidden" name="action" value="show_flights">
                <div class="form-label"><h2>Where do you want to go?</h2></div>
                <label for="from">From:</label>
                <%--                <jsp:useBean id="airports" scope="session" type="java.util.HashMap"/>--%>
                <%--                <c:set var="depart" value="${requestScope.departure}">--%>
                <select class="form-select me-2" id="from" name="departure"
                        onchange="updateOptions2(this.value)" required>
                    <option disabled value="">Select</option>
                    <c:forEach var="airport" items="${airports}">
                        <c:if test="${departure == airport.key}">
                            <option selected value="<c:out value="${airport.key}"/>"><c:out
                                    value="${airport.value} ( ${airport.key})"/></option>
                        </c:if>
                        <c:if test="${departure != airport.key}">
                            <option value="<c:out value="${airport.key}"/>"><c:out
                                    value="${airport.value} ( ${airport.key})"/></option>
                        </c:if>
                    </c:forEach>
                </select>
                <label for="to">To:</label>
                <select class="form-select me-2" id="to" name="destination"
                        onchange="updateOptions1(this.value)" required>
                    <option disabled value="">Select</option>
                    <c:forEach var="airport" items="${airports}">
                        <c:if test="${destination == airport.key}">
                            <option selected value="<c:out value="${airport.key}"/>"><c:out
                                    value="${airport.value} ( ${airport.key})"/></option>
                        </c:if>
                        <c:if test="${destination != airport.key}">
                            <option value="<c:out value="${airport.key}"/>"><c:out
                                    value="${airport.value} ( ${airport.key})"/></option>
                        </c:if>
                    </c:forEach>
                </select>
                <label for="depart-day">Depart day:</label>
                <input type="date" id="depart-day" name="departDay" class="form-control">
                <div class=" mt-3">
                    <button type="submit" class="btn btn-primary">Search flight</button>
                </div>
            </form>
        </div>
    </aside>
    <div class="col-4"></div>
    <div class="main col-8 container-fluid">
        <div class="mb-2 d-flex" style="z-index: 1">
            <a href="flight?action=sort&&sortType=best" class="col-4 d-flex text-decoration-none">
                <button style="--bs-btn-bg: #05203c" class="btn btn-lg btn-outline-light w-100">Best</button>
            </a>
            <a href="flight?action=sort&&sortType=cheapest" class="col-4 d-flex text-decoration-none">
                <button style="--bs-btn-bg: #05203c" class="btn btn-lg btn-outline-light w-100">Cheapest</button>
            </a>
            <a href="flight?action=sort&&sortType=earliest" class="col-4 d-flex text-decoration-none">
                <button style="--bs-btn-bg: #05203c" class="btn btn-lg btn-outline-light w-100">Earliest</button>
            </a>
        </div>
        <div>
            <ul class="list-group">
                <%--                <jsp:useBean id="flightCards" scope="request" type="java.util.List<dto.FlightCardDTO>"/>&ndash;%&gt;--%>
                <c:forEach var="flight" items="${flightCards}">
                    <li class="list-group-item d-flex mb-2 bg-dark-subtle bg-gradient">
                        <div class="col-9 border-end pe-1">
                            <div class="d-flex">
                                <div class="col-3 border-end">
                                    <img class="img-fluid" src="<c:out value="${flight.airlinesImgUrl}"></c:out>"
                                         alt="logo">
                                </div>
                                <div class="col-9 d-flex">
                                    <div class="col-4 text-end d-flex justify-content-center flex-column">
                                        <div><span class="fs-1 fw-semibold text-secondary">
                                            <c:out value="${flight.departTime}"></c:out></span></div>
                                        <div><span><c:out value="${flight.departure}"></c:out></span></div>
                                    </div>
                                    <div class="col-4 text-center d-flex justify-content-center flex-column">
                                        <div><span><c:out value="${flight.flightTime}"></c:out></span></div>
                                        <div>---------------<i class="fa-solid fa-plane fa-beat-fade"></i></div>
                                        <div><span class="text-info"><c:out value="${flight.flightCode}"></c:out></span>
                                        </div>
                                    </div>
                                    <div class="col-4 text-start d-flex justify-content-center flex-column">
                                        <div><span class="fs-1 fw-semibold text-secondary">
                                            <c:out value="${flight.arrivalTime}"></c:out></span></div>
                                        <div><span><c:out value="${flight.destination}"></c:out></span></div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-between">
                                <span class="col fw-light"><c:out value="${flight.airlinesName}"></c:out></span>
                                <span class="col text-end me-2"><c:out value="${flight.departDate}"></c:out></span>
                            </div>
                        </div>
                        <div class="col-3 ps-1 d-flex flex-column justify-content-between">
                            <div class="d-flex justify-content-end">
                                <div class="col-6">
                                    <c:if test="${flight.withBusinessClass == true}">
                                        <img class="img-fluid" alt=""
                                             src="https://media.discordapp.net/attachments/646934670679998474/1115522268374769724/business-removebg-preview.png?width=861&height=335">
                                    </c:if>
                                    <c:if test="${flight.withBusinessClass == false}">
                                        <img class="img-fluid" alt=""
                                             src="https://media.discordapp.net/attachments/646934670679998474/1115522268114731039/economy-removebg-preview.png?width=862&height=337">
                                    </c:if>
                                </div>
                            </div>
                            <div class="fs-3 fw-bold text-center"><c:out value="${flight.basePrice}"></c:out></div>
                            <div class="d-flex justify-content-center">
                                <form action="flight" method="post">
                                    <input type="hidden" name="flightID" value="${flight.id}">
                                    <input type="hidden" name="action" value="selectFlight">
                                    <button class="btn btn-lg btn-dark btn-outline-info">Select
                                        <i class="fa-solid fa-arrow-right fa-bounce" style="color: #ffffff;"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

    </div>
</div>

<%@include file="./WEB-INF/view/footer.jsp" %>
<%@include file="./WEB-INF/view/modal.jsp" %>

<%--//////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/d625a478fd.js" crossorigin="anonymous"></script>
<script>
    <%@include file="static/js/flight_control.js" %>
</script>
<script>
    <%@include file="/static/js/account.js" %>
</script>
</body>
</html>