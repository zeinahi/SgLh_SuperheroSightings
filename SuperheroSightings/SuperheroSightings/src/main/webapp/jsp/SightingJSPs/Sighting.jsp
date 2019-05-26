<%-- 
    Document   : Sighting
    Created on : Jan 1, 2019, 7:22:23 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sighting JSP </title>

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
    </head>
    <body>           
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
        <!-- 
       Add a row to our container - this will hold the summary table and the new
       contact form.
        -->
        <div class="row">
            <!-- 
                Add a col to hold the summary table - have it take up half the row 
            -->
            <div class="col-md-6">
                <h2>Sighting</h2>
                <table id="sightingTable" class="table table-hover">
                    <tr>
                        <th width="25%">Sighting Date</th>
                        <th width="25%">Person</th>
                        <th width="25%">Location</th>
                        <th> </th>
                        <th width="25%">Sighting Type</th>
                    </tr>

                    <c:forEach var="currentSighting" items="${sightings}">

                        <!--the forEach adds an extra row for each contact in the contact List with these td tags-->
                        <tr>

                            <td>
                                <a href="displaySightingDetails?sightingId=${currentSighting.sightingId}">
                                    <c:out value="${currentSighting.justTheSightingDate}"/>
                                </a>
                            </td>

                            <td>
                                 <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <a href="displayPersonDetails?personId=${currentSighting.person.personId}">
                                    <c:out value="${currentSighting.person.firstName}"/> <c:out value="${currentSighting.person.lastName}"/>
                                </sec:authorize>
                                </a>
                            </td>
                            <td>
                                 <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <a href="displayLocationDetails?locationId=${currentSighting.location.locationId}">
                                    <c:out value="${currentSighting.location.locationName}"/>
                                </sec:authorize>
                                </a>
                            </td>
                            <td>

                            <td>
                                <c:out value="${currentSighting.isHeroSighting}"/>

                            </td>

                            <td>
                                <a href="displayEditSightingForm?sightingId=${currentSighting.sightingId}">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <a href="deleteSighting?sightingId=${currentSighting.sightingId}">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

            </div>

             <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div class="col-md-6">
                <h2>Add New Sighting</h2>
                <form class="form-horizontal" 
                      role="form" method="POST" 
                      action="addSighting">

                    <!--                    <div class="form-group">
                                            <label for="add-first-name" class="col-md-4 control-label">First Name:</label>
                                            <div class="col-md-8">
                                                <input type="text" class="form-control" name="firstName" placeholder="First Name"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="add-last-name" class="col-md-4 control-label">Last Name:</label>
                                            <div class="col-md-8">
                                                <input type="text" class="form-control" name="lastName" placeholder="Last Name"/>
                                            </div>
                                        </div>-->
                    <div class="form-group">
                        <label for="add-isHero" class="col-md-4 control-label">Is this a superhero?:</label>
                        <div class="col-md-8">
                            <label class="choice" > <input type="radio" name="isHero" value="true"> True </label>
                            <label class="choice" > <input type="radio" name="isHero" value="false"> False </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-sightingDate" class="col-md-4 control-label">Sighting Date:</label>
                        <div class="col-md-8">
                            <input type="date" class="form-control" name="justTheSightingDate" placeholder="Sighting Date"/>
                        </div>
                    </div>




                    <div class="form-group">
                        <label for="add-Person" class="col-md-4 control-label">Person:</label>
                        <div class="col-md-8">
                            <select class="form-control" name="personId" placeholder="person"/>
                            <c:forEach var="currentPerson" items="${allPersons}">
                                <!--  Use option to get each person in the list of persons-->
                                <option value = "${currentPerson.personId}"> ${currentPerson.firstName} ${currentPerson.lastName}</option>
                            </c:forEach>
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="add-Location" class="col-md-4 control-label">Location:</label>
                        <div class="col-md-8">
                            <select class="form-control" name="locationId" placeholder="location"/>
                            <c:forEach var="currentLocation" items="${locationsSeen}">
                                <option value = "${currentLocation.locationId}"> ${currentLocation.locationName}</option>
                            </c:forEach>
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <input type="submit" class="btn btn-default" value="Add Superhero Sighting"/>
                        </div>
                    </div>


                </form>

            </div> <!-- End col div -->
             </sec:authorize>
        </div> <!-- End row div -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>                 


    </body>
</html>