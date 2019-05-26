<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Directive for Spring Form tag libraries -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title> Admin</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">

            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superpowerHome">Superpower</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/personHome">Person</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/locationHome">Location</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organizationHome">Organization</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/sightingHome">Sighting</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/userHome">User</a></li>
                </ul>
            </div>

            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name}
                    | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                </p>
            </c:if>
            <div class="container">
                <h1>Superhero Sightings</h1>
                <hr/>
                <div class="navbar">
                    <ul class="nav nav-tabs">
                        <li role="presentation">
                            <a href="${pageContext.request.contextPath}/home">
                                Home
                            </a>
                        </li>
                        <li role="presentation">
                            <a href="${pageContext.request.contextPath}/user">
                                user(must have the user role)
                            </a>
                        </li>
                        <li role="presentation" class="active">
                            <a href="${pageContext.request.contextPath}/admin">
                                Admin (must have the admin role)
                            </a>
                        </li>
                    </ul>    
                </div>
                <h2>Admin Login</h2>

                <p>
                    Only users with the ADMIN role can see this page.
                </p>
            </div>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>