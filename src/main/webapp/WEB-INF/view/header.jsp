<header class="header sticky-top">
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
                        <a class="nav-link active" aria-current="page" href="home">Home</a>
                    </li>
                    <li class="nav-item me-3">
                        <a class="nav-link" href="WEB-INF/view/404_page.jsp">Link</a>
                    </li>
                    <li class="nav-item me-3 dropdown">
                        <a class="nav-link dropdown-toggle" href="" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false" id="about">
                            About
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="about" data-bs-popper="static">
                            <li>
                                <button class="dropdown-item">Discovery</button>
                            </li>
                            <li><a class="dropdown-item" href="#">Nothing here</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">Coming soon (not sure)</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a href="https://chat.openai.com/" target="_blank" class="nav-link">Ask me everything</a>
                    </li>
                </ul>
                <%--                <jsp:useBean id="user" scope="session" type="model.abstraction.User"/>--%>
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

                <c:if test="${user!=null}">
                    <div class="navbar-nav dropdown mb-2 mb-lg-0 d-flex justify-content-center pe-3">
                        <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" href="#">
                            <i class="fa-solid fa-user fa-xl " style="color: #723f03;"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="about" data-bs-popper="static">
                            <li>
                                <a class="dropdown-item" href="home?action=profile">
                                    <c:if test="${user.fullName == null}">
                                        <c:out value="${user.userName}"></c:out>
                                    </c:if>
                                    <c:if test="${user.userName != null}">
                                        <c:out value="${user.fullName}"></c:out>
                                    </c:if>
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="ticket?action=profile">View booked ticket</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="home?action=logout">Log out</a></li>
                        </ul>
                    </div>
                </c:if>
            </div>
        </div>
    </nav>
</header>
<div class="position-fixed sticky-top" style="z-index: -1">
    <img src="https://cdn.discordapp.com/attachments/646934670679998474/1114882026542202920/148566.jpg" alt="background">
</div>