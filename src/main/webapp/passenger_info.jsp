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
</head>
<body>
<%@include file="./WEB-INF/view/header.jsp" %>
<div class="position-fixed sticky-top" style="z-index: -1; width: 100%">
    <img src="https://cdn.discordapp.com/attachments/646934670679998474/1117000053555466331/universe-space-stars-digital-art-wallpaper-7bb61c6d53e13f7995f4fba93d5c0c70.jpg"
         alt="background" style="width: 100%">
</div>

<div class="main-content container-fluid mt-5 d-flex justify-content-center">
    <div class="col-8 mt-4 bg-dark bg-opacity-50" style="border-radius: 10px">
        <form method="post" action="ticket?action=passengerInfoSubmit" class="row p-4 text-light">
            <div class="form-label"><h2>Passenger info</h2></div>
            <div class="col-md-4">
                <label for="firstname" class="form-label">First name</label>
                <input type="text" class="form-control" name="firstname" id="firstname" placeholder="e.g. Mary Isabelle" required>
            </div>
            <div class="col-md-4">
                <label for="lastname" class="form-label">Last name</label>
                <input type="text" class="form-control" name="lastname" id="lastname" placeholder="e.g. Smith" required>
            </div>
            <div class="col-md-4">
                <label for="gender" class="form-label">Gender</label>
                <select class="form-select" aria-label="Default select example" name="gender" id="gender" required>
                    <option selected disabled>Select gender</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                </select>
            </div>
            <div class="col-md-8">
                <label for="idOrPassport" class="form-label">Your ID or Passport Number</label>
                <input type="text" class="form-control" name="idOrPassport" id="idOrPassport" required>
            </div>
            <div class="col-md-4">
                <label for="email" class="form-label">Contact Email</label>
                <input type="email" class="form-control" placeholder="To receive booking confirmation" name="contactEmail" id="email" required>
            </div>
            <div class="col-md-6">
                <label for="address" class="form-label">Your Address</label>
                <input type="text" class="form-control" id="address">
            </div>
            <div class="col-md-6">
                <label for="phoneNumber" class="form-label">Contact Phone Number</label>
                <input type="text" class="form-control" id="phoneNumber" placeholder="Phone Number" aria-label="Text input with dropdown button">
            </div>
            <div class="col-12 mt-4 d-flex flex-column justify-content-center">
                <div class="form-check m-2">
                    <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault" required>
                    <label class="form-check-label" for="flexCheckDefault">
                        I have read and agreed to the following Isekai.com booking terms and conditions: <a href="">Flight Booking Policies</a> ,
                        <a href="">Privacy Statement</a> .
                    </label>
                </div>
                <button class="btn btn-lg btn-warning col-md-12" type="submit">Next</button>
            </div>
        </form>
    </div>
</div>

<%@include file="./WEB-INF/view/footer.jsp" %>
</body>
</html>