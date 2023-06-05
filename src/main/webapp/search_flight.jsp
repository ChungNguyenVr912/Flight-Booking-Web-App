<%@ page import="model.abstraction.User" %>
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
    <link rel="stylesheet" href="CSS/account.css" type="text/css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
          rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body class="bg-dark-subtle">
<header class="sticky-top">
    <nav class="navbar navbar-expand-lg bg-warning bg-gradient">
        <div class="container-fluid">
            <img src="https://media.discordapp.net/attachments/646934670679998474/1114554151666462822/Untitled222-removebg-preview.png?width=821&height=340"
                 alt="logo" class="img-fluid" style="max-height: 80px">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item me-3">
                        <a class="nav-link active" aria-current="page" href="#">Home</a>
                    </li>
                    <li class="nav-item me-3">
                        <a class="nav-link" href="#">Link</a>

                    </li>
                    <li class="nav-item me-3 dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false" id="about">
                            About
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="about" data-bs-popper="static">
                            <li>
                                <button class="dropdown-item">Action</button>
                            </li>
                            <li><a class="dropdown-item" href="#">Another action</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">Something else here</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled">Feature</a>
                    </li>
                </ul>

                <c:if test="${user == null}">
                    <div class="d-flex">
                        <button class="btn btn-lg btn-light me-3" type="button" data-bs-toggle="modal"
                                data-bs-target="#signInModal">Sign In
                        </button>
                        <button class="btn btn-lg btn-secondary me-2" type="button" data-bs-toggle="modal"
                                data-bs-target="#signUpModal">Sign Up
                        </button>
                    </div>
                </c:if>

                <%--            <jsp:useBean id="user" scope="request" type="model.abstraction.User"/>--%>
                <%
                    User user = (User) session.getAttribute("user");
                %>
                <c:if test="${user!=null}">
                    <div class="navbar-nav mb-2 mb-lg-0 d-flex justify-content-center">
                        <p class="nav-item ">Hello:</p>
                        <p class="nav-item d-flex justify-content-center">
                            <c:out value="${user.fullName}"/>
                        </p>
                        <a href="">
                            <button class="btn btn-lg btn-secondary ms-2 me-2" type="button">Log out
                            </button>
                        </a>
                    </div>
                </c:if>

            </div>
        </div>
    </nav>
</header>

<div class="container-fluid pt-3">
    <aside class="bd-sidebar col-4 bg-dark-subtle">
        <%--    <img src="https://media.discordapp.net/attachments/646934670679998474/1114010046293082182/Untitled2.png?width=683&height=662"--%>
        <%--         alt="web-logo" class="img-fluid d-block" style="max-height: 200px">--%>
        <div class="container-fluid" id="select-location">
            <form action="flight_controller" method="post" class="form-control">
                <input type="hidden" name="action" value="show_flights">
                <div class="form-label"><h2>Where you want to go?</h2></div>
                <label for="from" class="mb-1">From:</label>
                <div class="d-flex">
                    <span class="align-content-center me-2">
                        <i class="fa-solid fa-plane-departure fa-beat-fade fa-lg" style="color: #3d84ff"></i>
                    </span>
                    <select class="form-select" id="from" onchange="updateOptions2(this.value)" required>
                        <option selected disabled value="">Select</option>
                        <option value="HAN">Hà Nội (HAN)</option>
                        <option value="HPH">Hải Phòng (HPH)</option>
                        <option value="HUI">Huế (HUI)</option>
                    </select>
                </div>
                <label for="to" class="mb-1">To:</label>
                <div class="d-flex">
                    <span class="align-content-center me-2">
                        <i class="fa-solid fa-plane-arrival fa-beat-fade fa-lg" style="color: #3d84ff"></i>
                    </span>
                    <select class="form-select" id="to" onchange="updateOptions1(this.value)" required>
                        <option selected disabled value="">Select</option>
                        <option value="HAN">Hà Nội (HAN)</option>
                        <option value="HPH">Hải Phòng (HPH)</option>
                        <option value="HUI">Huế (HUI)</option>
                    </select>
                </div>
                <div class="d-flex justify-content-end mt-3">
                    <button type="submit" class="btn" style="--bs-btn-bg: #ffe69c; --bs-btn-hover-color: #ffffff; --bs-btn-hover-bg:#6e6e6e">Search flight</button>
                </div>
            </form>
        </div>
    </aside>
    <div class="col-8">

    </div>
</div>


<div class="modal fade " id="signUpModal" tabindex="-1" aria-labelledby="exampleModalCenterTitle"
     style="display: none; --bs-modal-width:40%" aria-modal="true" role="dialog">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalCenterTitle">Create account</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body ">
                <form method="post" action="account" class="form-control needs-validation" novalidate>
                    <input type="hidden" name="action" value="create_account">
                    <div class="row justify-content-center mb-4">
                        <div class="col-12">
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" id="username" name="username"
                                       placeholder="username" minlength="4" maxlength="20" required>
                                <label for="username">User name</label>
                                <div class="invalid-feedback">Please fill out this field. As least 4, max 20
                                    characters!
                                </div>
                                <small class="text-danger" id="invalid-username"></small>
                                <small class="text-success" id="valid-username"></small>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="form-floating mb-3">
                                <input type="email" class="form-control" id="email" name="email"
                                       placeholder="name@example.com" required>
                                <label for="email">Email address</label>
                                <div class="invalid-feedback">name@example.com</div>
                                <small class="text-danger" id="invalid-email"></small>
                                <small class="text-success" id="valid-email"></small>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="form-floating">
                                <input type="password" class="form-control" minlength="6" maxlength="20" id="password"
                                       name="password"
                                       aria-labelledby="passwordHelpBlock" placeholder="Password" required>
                                <label for="password">Password</label>
                                <div class="invalid-feedback">At least 6, max 20 characters</div>
                            </div>
                            <div id="passwordHelpBlock" class="invalid-feedback">
                                Your password must be 8-20 characters long, contain letters, numbers, special
                                characters.
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-6">
                            <select class="form-select mb-2" name="gender"
                                    aria-label=".form-select-lg example" required>
                                <option selected disabled value="">Select gender</option>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="" id="invalidCheck" required>
                            <label class="form-check-label" for="invalidCheck">
                                Agree to terms and conditions
                            </label>
                            <div class="invalid-feedback">
                                You must agree before submitting.
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer d-flex justify-content-center mt-2">
                        <div class="d-grid col-12 mx-auto">
                            <button class="btn btn-primary btn-lg " style="background: #0060ff"
                                    type="submit">Sign Up
                            </button>
                        </div>
                        <p class="fw-normal ">Already have account?</p>
                        <p>
                            <a class="link-secondary link-offset-2 link-underline-opacity-10 link-underline-opacity-100-hover"
                               data-bs-target="#signInModal" data-bs-toggle="modal" style="text-align: center">Sign
                                In</a></p>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="signInModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form method="post" action="account" id="loginForm">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Sign In</h1>
                    <input type="hidden" name="action" value="login">
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="loginUsernameOrEmail" name="loginUsernameOrEmail"
                               placeholder="name@example.com" required>
                        <label for="loginUsernameOrEmail">Username or Email</label>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control" id="loginPassword" name="loginPassword"
                               placeholder="Password" required>
                        <label for="loginPassword">Password</label>
                    </div>
                    <small class="text-danger mt-4" id="loginFeedback"></small>
                </div>
                <div class="modal-footer d-flex justify-content-center">
                    <div class="d-grid col-12 mx-auto">
                        <button class="btn btn-primary btn-lg " style="background: #0060ff"
                                type="submit">Sign In
                        </button>
                    </div>
                    <a class="link-secondary link-offset-2 link-underline-opacity-10 link-underline-opacity-100-hover"
                       data-bs-target="#signUpModal" data-bs-toggle="modal" style="text-align: center">Create
                        account</a>
                </div>
            </form>
        </div>
    </div>
</div>


<%--//////////////////////////////////////////////////////////////////////////////////////////////////////////--%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/d625a478fd.js" crossorigin="anonymous"></script>
<script src="JS/account.js"></script>
<script src="JS/flight_control.js"></script>
</body>
</html>