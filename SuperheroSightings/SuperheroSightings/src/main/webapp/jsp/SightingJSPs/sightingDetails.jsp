<%-- 
    Document   : displaySightingDetails
    Created on : Jan 16, 2019, 2:42:54 PM
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
        <title>Sighting Details </title>

        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
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
            
              <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name}
                    | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                </p>
            </c:if>
            
        <h1>Sighting Details</h1>
        <hr/>
        <div class="row">
            <div class="col-md-12">
                <h3>
                    Sighting Details for ${sightingDetails.justTheSightingDate}
                </h3>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2">
            </div>
            <div class="col-md-8">
                <div class="row">
                    <div class="col-md-12">
                        <div class="row">


                            <div class="col-md-2">
                            </div>


                            <div class="col-md-8">
                                <h3 class = "miniTable">
                                    Person
                                </h3>

                                <table id="sightingTable" class="table table-hover">

                                    <tr>
                                        <th width="25%">Person Name</th>
                                        <th width="25%">Person Type</th>
                                        <th width="25%">Superpowers</th>
                                        <th width="25%">Person Description</th>
                                    </tr>


                                    <tr>

                                        <td>
                                            <a href="displaySightingDetails?personId=${sightingDetails.person.personId}">
                                                <c:out value="${sightingDetails.person.firstName}"/> <c:out value="${sightingDetails.person.lastName}"/>
                                            </a>
                                        </td>

                                        <td>
                                            <c:out value="${sightingDetails.person.isHero}"/>
                                        </td>  

                                        <td>
                                            <c:forEach var="currentSuperpower" items="${personSPs}">
                                                <c:out value="${currentSuperpower.superpower}"/>
                                                <br>

                                            </c:forEach>

                                        </td>   

                                        <td>
                                            <c:out value="${sightingDetails.person.descriptionOfPerson}"/>
                                        </td>                                          


                                    </tr>

                                </table>

                                <!--                          End of table-->

                            </div>


                            <div class="col-md-2">
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-12">
                        <div class="row">

                            <div class="col-md-2">
                            </div>


                            <div class="col-md-8">
                                <h3>
                                    Location
                                </h3>


                                <table id="sightingTable" class="table table-hover">

                                    <tr>
                                        <th width="25%">Location Name</th>
                                        <th width="25%">Location Address</th>
                                        <th width="50%">Location Description</th>
                                    </tr>


                                    <tr>

                                        <td>
                                            <a href="displayLocationDetails?locationId=${sightingDetails.location.locationId}">
                                                <c:out value="${sightingDetails.location.locationName}"/>
                                            </a>
                                        </td>

                                        <td>
                                            <c:out value="${sightingDetails.location.locationDescription}"/>
                                        </td>    

                                        <td>
                                            <c:out value="${sightingDetails.location.locationCountry}"/> <c:out value="${sightingDetails.location.locationState}"/> <c:out value="${sightingDetails.location.locationCity}"/>
                                            <c:out value="${sightingDetails.location.locationStreet}"/> <c:out value="${sightingDetails.location.locationZipcode}"/>
                                            <br>
                                            <c:out value="${sightingDetails.location.latitude}"/> <c:out value="${sightingDetails.location.longitude}"/>
                                        </td>  


                                    </tr>

                                </table>      


                            </div>


                            <div class="col-md-2">
                            </div>


                        </div>
                    </div>
                </div>





                <!--All Sightings By Date-->                   


                <div class="row">
                    <div class="col-md-12">
                        <div class="row">

                            <div class="col-md-2">
                            </div>


                            <div class="col-md-8">
                                <h3 class = "miniTable">
                                    Sighting For This Date
                                </h3>


                                <table id="sightingTable" class="table table-hover">
                                    <tr>
                                        <th width="30%">Date</th>
                                        <th width="30%">Name</th>
                                        <th width="40%">Location</th>
                                    </tr>



                                    <c:forEach var="currentSighting" items="${specificSightingDateTime}">

                                        <!--the forEach adds an extra row for each contact in the contactList with these td tags-->

                                        <tr>

                                            <td>
                                                <a href="displaySighting?sightingId=${currentSighting.sightingId}"><c:out value="${currentSighting.sightingDate}"/></a>
                                            </td>

                                            <td>

                                                <a href="displayPersonDetails?personId=${currentSighting.person.personId}"><c:out value="${currentSighting.person.firstName}"/><c:out value="${currentSighting.person.lastName}"/></a>
                                            </td>


                                            <td>
                                                <a href="displayLocationDetails?locationId=${currentSighting.location.locationId}"><c:out value="${currentSighting.location.locationName}"/></a>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </table>   

                            </div>

                            <div class="col-md-2">
                            </div>


                        </div>
                    </div>
                </div>                  


            </div>

            <div class="col-md-2">
            </div>

        </div>
    </div>


    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>                 


</body>
</html>