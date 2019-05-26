<%-- 
    Document   : editSighting
    Created on : Jan 16, 2019, 2:43:06 PM
    Author     : zissah
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Directive for Spring Form tag libraries -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Location</title>

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



        <div class="container">
            <h1>Edit Sighting</h1>
            <hr/>  
        </div>

        ${sightingForEdit.getSightingDate()}
        <sf:form class="form-horizontal" role="form" modelAttribute="sightingForEdit"
                 action="editSighting" method="POST">

            <div class="form-group">
                <div class="form-group">
                    <label for="justTheSightingDate" class="col-md-4 control-label">Sighting Date:</label>
                    <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="justTheSightingDate"
                                  path="justTheSightingDate" placeholder="justTheSightingDate"/>
                        <sf:errors path="justTheSightingDate" cssclass="error"></sf:errors>     
                        <sf:hidden path="SightingId"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="select-Location" class="col-md-4 control-label">Location:</label>
                        <div class="col-md-8">
                        <sf:select path="location.locationId">
                            <sf:options items="${allLocations}" itemValue="locationId" itemLabel="locationName" ></sf:options>
                        </sf:select>

                    </div>
                </div>
                <div class="form-group">
                    <label for="select-Person" class="col-md-4 control-label">Person:</label>
                    <div class="col-md-8">
                        <sf:select path="person.personId">
                            <sf:options items="${allPersons}" itemValue="personId" itemLabel="firstName" ></sf:options>
                        </sf:select>

                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Sighting"/>
                    </div>
                </div>
            </sf:form>  



            <!-- Placed at the end of the document so the pages load faster -->
            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
