<%-- 
    Document   : editOrganization
    Created on : Jan 16, 2019, 2:36:17 PM
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
        <title>Edit Organization</title>
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
            <h1>Edit Organization</h1>
            <hr/>  
        </div>

        <sf:form class="form-horizontal" role="form" modelAttribute="organizationForEdit"
                 action="editOrganization" method="POST">

            <div class="form-group">
                <label for="add-OrganizationName" class="col-md-4 control-label">Organization Name:</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" name="organizationName" placeholder="Organization Name"/>
                </div>
            </div>
            <div class="form-group">
                <label for="add-OrganizatioDescription" class="col-md-4 control-label">Organization Description:</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" name="organizationDescription" placeholder="Organization Description"/>
                </div>
            </div>
            <div class="form-group">
                <label for="add-OrganizationCountry" class="col-md-4 control-label">Organization Country:</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" name="organizationCountry" placeholder="Organization Country"/>
                </div>
            </div>
            <div class="form-group">
                <label for="add-OrganizationState" class="col-md-4 control-label">Organization State:</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" name="organizationState" placeholder="Organization State"/>
                </div>
            </div>
            <div class="form-group">
                <label for="add-OrganizationCity " class="col-md-4 control-label">Organization City:</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" name="organizationCity" placeholder="Organization City"/>
                </div>
            </div>
            <div class="form-group">
                <label for="add-OrganizationStreet " class="col-md-4 control-label">Organization Street:</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" name="organizationStreet" placeholder="Organization Street"/>
                </div>
            </div>

            <div class="form-group">
                <label for="add-OrganizationZipCode " class="col-md-4 control-label">Organization ZipCode:</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" name="organizationZipCode" placeholder="Organization ZipCode"/>
                </div>
            </div>

            <div class="form-group">
                <label for="add-IsHeroOrganizatione " class="col-md-4 control-label">Is this a hero Organization:</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" name="isHeroOrganization" placeholder="is Hero Organization"/>
                </div>



                <div class="form-group">
                    <label for="add-personInOrganization" class="col-md-4 control-label">Persons:</label>
                    <div class="col-md-8">
                        <sf:select path="allOrganizationIdsForOrganizationListInOrganizationDTO">
                            <sf:options items="${personsList}"  itemLabel="firstName" itemValue="personId"/>
                        </sf:select>
                    </div>
                </div>                             


                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Organization"/>
                    </div>
                </div>
            </sf:form>


    </body>
</html>
