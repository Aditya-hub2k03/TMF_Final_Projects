<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Ticket Booking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <style>
        .user-role-badge {
            font-size: 0.75rem;
            margin-left: 5px;
        }
        .dropdown-item.active {
            background-color: #0d6efd;
            color: white;
        }
        .search-box {
            width: 300px;
        }
        @media (max-width: 991.98px) {
            .search-box {
                width: 100%;
                margin-bottom: 10px;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Movie Ticket Booking</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/movie/list">Movies</a>
                    </li>
                    <c:if test="${not empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/booking/my-bookings">My Bookings</a>
                        </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.user && sessionScope.user.role == 'APPLICATION_ADMIN'}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard">Admin</a>
                        </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.user && sessionScope.user.role == 'THEATRE_ADMIN'}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/theatre/dashboard">Theatres</a>
                        </li>
                    </c:if>
                </ul>

                <!-- Search Box -->
                <form class="d-flex me-3" action="${pageContext.request.contextPath}/movie/search" method="get">
                    <div class="input-group search-box">
                        <input type="text" class="form-control" name="query" placeholder="Search movies..." aria-label="Search movies">
                        <button class="btn btn-outline-light" type="submit">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                            </svg>
                        </button>
                    </div>
                </form>

                <ul class="navbar-nav">
                    <c:if test="${empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/user/register">Register</a>
                        </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.user}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                Welcome, ${sessionScope.user.username}
                                <span class="user-role-badge badge bg-secondary">
                                    <c:choose>
                                        <c:when test="${sessionScope.user.role == 'APPLICATION_ADMIN'}">Admin</c:when>
                                        <c:when test="${sessionScope.user.role == 'THEATRE_ADMIN'}">Theatre Admin</c:when>
                                        <c:otherwise>User</c:otherwise>
                                    </c:choose>
                                </span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/profile">Profile</a></li>
                                <c:if test="${sessionScope.user.role == 'THEATRE_ADMIN'}">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/theatre/dashboard">Theatre Dashboard</a></li>
                                </c:if>
                                <c:if test="${sessionScope.user.role == 'APPLICATION_ADMIN'}">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/dashboard">Admin Dashboard</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/city/list">Manage Cities</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/promotion/list">Manage Promotions</a></li>
                                </c:if>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/logout">Logout</a></li>
                            </ul>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container mt-4">
