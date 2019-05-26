<%-- 
    Document   : displayLocationDetails
    Created on : Jan 16, 2019, 2:31:38 PM
    Author     : zissah
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Directive for Spring Form tag libraries -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Location Details</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">     
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">       
    </head>
    <body>
        <hr/>
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


        <div class="container">


            <div class="row">
                <div class="col-md-2">
                </div>
                <div class="col-md-8 wholePage">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="col-md-2">
                                </div>
                                <div class="col-md-8 firstTable">
                                    <h3 class = "Label">
                                        Location Details 
                                    </h3>

                                    <table id="locationTable" class="table table-hover">
                                        <br>
                                        <tr>
                                            <th width="30%">Location Name</th>
                                            <th width="35%">Location Address</th>
                                            <th width="35%">Location Description</th>
                                        </tr>     

                                        <c:forEach var ="locationDetails" items ="${locationList}">

                                            <tr>
                                                <td>
                                                    <a href="locationDetails?locationId=${locationDetails.locationId}">
                                                        <c:out value ="${locationDetails.locationName}"/>
                                                    </a>
                                                </td>

                                                <td>
                                                    <c:out value ="${locationDetails.locationStreet} ${locationDetails.locationCity}
                                                           ${locationDetails.locationState} ${locationDetails.locationZipcode} ${locationDetails.locationCountry}"/>
                                                    <br>
                                                    <c:out value ="${locationDetails.latitude}"/>
                                                    <br>
                                                    <c:out value ="${locationDetails.longitude}"/>

                                                </td>

                                                <td>
                                                    <c:out value ="${locationDetails.locationDescription}"/>
                                                </td>            
                                            </tr>
                                        </c:forEach>
                                    </table>   
                                </div>



                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">

                                            <div class="col-md-2">
                                            </div>


                                            <div class="col-md-8 secondTable">
                                                <div class="container">
                                                    <h3 class = "Label">
                                                        All Sightings At Location
                                                    </h3>


                                                    <table id="sightingTable" class="table table-hover">
                                                        <tr>
                                                            <th width="35%">Sighting Date</th>
                                                            <th width="35%">Sighting Name</th>
                                                            <th width="15%"></th>
                                                            <th width="15%"></th>
                                                        </tr>


                                                        <c:forEach var="sightingDetails" items="${allSightsByLocation}">

                                                            <tr>
                                                                <td>
                                                                    <a href="displaySightingDetails?sightingId=${sightingDetails.sightingId}">
                                                                        <c:out value="${sightingDetails.justTheSightingDate}"/>
                                                                    </a>
                                                                <td>
                                                                    <a href="displayPersonDetails?personId=${sightingDetails.person.personId}">
                                                                        <c:out value="${sightingDetails.person.firstName}"/><c:out value="${sightingDetails.person.lastName}"/>
                                                                    </a>
                                                                </td>
                                                             
                                                            </tr>
                                                        </c:forEach>
                                                    </table>    

                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-2">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
