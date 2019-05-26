<%--
    Document   : User
    Created on : Jan 21, 2019, 5:30:03 PM
    Author     : zissah
--%>
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
        <title>User JSP</title>
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

                <hr/>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <h2>User Login</h2>


                <c:forEach var="currentUser" items="${allUsers}">
                    <tr>
                        <td>
                            <a href="displayUserDetails?userId=${currentUser.userId}">
                                <c:out value="${currentUser.userName}"/>
                            </a>
                        </td>
                        <td>
                            <c:out value="${currentUser.userPassword}"/>
                        </td>
                        <td>
                            <a href="displayEditUserForm?userId=${currentUser.userId}">
                                Edit
                            </a>
                        </td>
                        <td>
                            <a href="deleteUser?userId=${currentUser.userId}">
                                Delete
                            </a>
                        </td>

                    </tr>
                </c:forEach>

                <form class="form-horizontal"
                      role="form"
                      method="post"
                      action="j_spring_security_check">
                    <div class="form-group">
                        <label for="username" class="col-md-4 control-label">Username:</label>
                        <div class="col-md-8">
                            <input type="text"
                                   class="form-control"
                                   name="username"
                                   placeholder="Username"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="j_password" class="col-md-4 control-label">Password:</label>
                        <div class="col-md-8">
                            <input type="text"
                                   class="form-control"
                                   name="j_password"
                                   placeholder="Password"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <input type="submit"
                                   class="btn btn-default"
                                   id="search-button"
                                   value="Sign In"/>
                        </div>
                    </div>
                </form>
                </sec:authorize>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>