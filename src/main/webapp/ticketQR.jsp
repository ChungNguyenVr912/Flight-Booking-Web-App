<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/3/2023
  Time: 4:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.google.zxing.BarcodeFormat" %>
<%@ page import="com.google.zxing.EncodeHintType" %>
<%@ page import="com.google.zxing.Writer" %>
<%@ page import="com.google.zxing.WriterException" %>
<%@ page import="com.google.zxing.common.BitMatrix" %>
<%@ page import="com.google.zxing.qrcode.QRCodeWriter" %>
<%@ page import="com.google.zxing.qrcode.decoder.ErrorCorrectionLevel" %>
<%@ page import="java.util.HashMap" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<header class="sticky-top">
    <nav class="navbar navbar-expand-lg bg-dark-subtle">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Navbar</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Link</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">
                            Dropdown
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Action</a></li>
                            <li><a class="dropdown-item" href="#">Another action</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">Something else here</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled">Disabled</a>
                    </li>
                </ul>

<%--                <c:if test="${user == null}">--%>
<%--                    <div class="d-flex">--%>
<%--                        <button class="btn btn-warning btn-outline-secondary me-2" type="button" data-bs-toggle="modal"--%>
<%--                                data-bs-target="#signInModal">Sign In--%>
<%--                        </button>--%>
<%--                        <button class="btn btn-secondary me-2" type="button" data-bs-toggle="modal"--%>
<%--                                data-bs-target="#signUpModal">Sign Up--%>
<%--                        </button>--%>
<%--                    </div>--%>
<%--                </c:if>--%>

                <%--            <jsp:useBean id="user" scope="request" type="model.abstraction.User"/>--%>

<%--                <c:if test="${user!=null}">--%>
<%--                    <div class="navbar-nav mb-2 mb-lg-0 d-flex justify-content-center">--%>
<%--                        <p class="nav-item ">Hello:</p>--%>
<%--                        <p class="nav-item d-flex justify-content-center">--%>
<%--                            <c:out value="${user.fullName}"/>--%>
<%--                        </p>--%>
<%--                        <a href="">--%>
<%--                            <button class="btn btn-secondary ms-2" type="button">Log out--%>
<%--                            </button>--%>
<%--                        </a>--%>
<%--                    </div>--%>
<%--                </c:if>--%>

            </div>
        </div>
    </nav>
</header>
<%
    String qrData = "Here is your ticket\nCo cai nit!";
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
    out.println("<div class = 'container-fluid justify-content-center mt-3'>");
    out.println("<div class = 'col-3'>");
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
    out.println("</div>");
    out.println("</div>");
%>
</body>
</html>
