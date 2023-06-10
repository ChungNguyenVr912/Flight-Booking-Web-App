<%@ page import="model.abstraction.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<body style="background-image: url('https://cdn.discordapp.com/attachments/646934670679998474/1114882026542202920/148566.jpg');
 background-size: cover">
<%@include file="./WEB-INF/view/header.jsp"%>

<div class="main-content container-fluid my-md-4 d-flex justify-content-center">
    <div class="col-8 mt-5">
        <div class="container-fluid d-flex" id="select-location">
            <form action="flight" method="post" class="form-control">
                <input type="hidden" name="action" value="show_flights">
                <div class="form-label"><h2>Where you want to go?</h2></div>
                <label for="from">From:</label>
                <%--<jsp:useBean id="airports" scope="session" type="java.util.HashMap"/>--%>
                <select class="form-select me-2" id="from" name="departure" onchange="updateOptions2(this.value)" required>
                    <option selected disabled value="">Select</option>
                    <c:forEach var="airport" items="${airports}">
                        <option value="<c:out value="${airport.key}"/>"><c:out
                                value="${airport.value} ( ${airport.key})"/></option>
                    </c:forEach>
                </select>
                <label for="to">To:</label>
                <select class="form-select me-2" id="to" name="destination" onchange="updateOptions1(this.value)" required>
                    <option selected disabled value="">Select</option>
                    <c:forEach var="airport" items="${airports}">
                        <option value="<c:out value="${airport.key}"/>"><c:out
                                value="${airport.value} ( ${airport.key})"/></option>
                    </c:forEach>
                </select>
                <label for="depart-day">Depart day:</label>
                <input type="date" id="depart-day" name="departDay" class="form-control">
                <div class=" mt-3">
                    <button type="submit" class="btn btn-primary">Search flight</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="./WEB-INF/view/footer.jsp"%>
<%@include file="./WEB-INF/view/modal.jsp"%>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/d625a478fd.js" crossorigin="anonymous"></script>
<%--<script src="<c:url value="/static/js/flight_control.js"/>"></script>--%>
<script><%@include file="static/js/flight_control.js"%></script>
<script><%@include file="/static/js/account.js"%></script>
</body>
</html>