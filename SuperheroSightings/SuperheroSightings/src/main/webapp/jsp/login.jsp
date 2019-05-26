<%-- 
    Document   : login
    Created on : Jan 24, 2019, 6:35:57 PM
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
        <title>User Login</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Superhero Sightings</h1>
            <hr/>

            <nav class="navbar">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span> 
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Superhero Sightings</a>
                    </div>

                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <p>Hello : ${pageContext.request.userPrincipal.name}
                            | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                        </p>
                    </c:if>

                    <h2>Login Page</h2>

                    <!-- username and password inputs have to be set j_username and j_password-->
                    <c:if test="${param.login_error == 1}">
                        <h3>Wrong id or password!</h3>
                    </c:if>

                    <!--"action" here must match w/ login-processing-url value in the spring-security.xml-->    
                    <form class="form-horizontal" 
                          role="form" 
                          method="post" 
                          action="j_spring_security_check">
                        <div class="form-group">
                            <label for="j_username" class="col-md-4 control-label">Username:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="j_username" placeholder="Username"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="j_password" class="col-md-4 control-label">Password:</label>
                            <div class="col-md-8">
                                <input type="text" class="form-control" name="j_password" placeholder="Password"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-4 col-md-8">
                                <input type="submit" class="btn btn-default" id="search-button" value="Sign In"/>
                            </div>
                        </div>
                    </form>


                </div>
                <!-- Placed at the end of the document so the pages load faster -->
                <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
                <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

                </body>
                </html>
