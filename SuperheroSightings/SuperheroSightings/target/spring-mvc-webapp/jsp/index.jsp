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
        <title>Index Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
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

                    
                               <!-- Log In -->
<div class="col-md-offset-11 col-md-0"> 
    <a href="${pageContext.request.contextPath}/login">
    <button type="submit" class="btn btn-primary" value="logInButton">Log In</button>
    </a>
</div>  
                    
                    
                    <div class="navbar">
                        <ul class="nav nav-tabs">
                            <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                            <li role="presentation"><a href="${pageContext.request.contextPath}/superpowerHome">Superpower</a></li>
                            <li role="presentation"><a href="${pageContext.request.contextPath}/personHome">Person</a></li>
                            <li role="presentation"><a href="${pageContext.request.contextPath}/locationHome">Location</a></li>
                            <li role="presentation"><a href="${pageContext.request.contextPath}/organizationHome">Organization</a></li>
                            <li role="presentation"><a href="${pageContext.request.contextPath}/sightingHome">Sighting</a></li>
                           
                        </ul>
                    </div>


                    
                        <div class="col-md-6"
                             <h2>10 Most Recent Sightings</h2>
                            <table class="table  table-hover">
                                <thead
                                    <tr>
                                        <th>Date</th>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Location</th>
                                    </tr> 
                                </thead>
                                <tbody>
                                    <c:forEach var="currentSighting" items="${lastTenSightings}">
                                        <tr>
                                            <td>${currentSighting.justTheSightingDate}</td>

                                            <td>${currentSighting.person.firstName}</td>

                                            <td>${currentSighting.person.lastName}</td>

                                            <td>${currentSighting.location.locationName}</td>
                                        </tr>
                                    </c:forEach>

                                </tbody>
                            </table>


                    <h4> Hope you enjoyed the sightings of Superheros and Supervillians. 
                        Feel free to report any sighting you observe using the Sightings tab </h4>
                        </div>

                 
                        <!-- Placed at the end of the document so the pages load faster -->
                        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
                        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

                        </body>
                        </html>

