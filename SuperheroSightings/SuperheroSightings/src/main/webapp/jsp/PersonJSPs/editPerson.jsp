<%-- 
    Document   : editPerson
    Created on : Jan 16, 2019, 2:40:04 PM
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
        <title>Edit Person</title>

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
            <h1>Edit Person</h1>
            <hr/>  
        </div>

        <sf:form class="form-horizontal" role="form" modelAttribute="personForEdit"
                 action="editPerson" method="POST">
            <div class="form-group">
                <label for="add-personFirstName" class="col-md-4 control-label">First Name:</label>
                <div class="col-md-8">
                    <sf:input type="text" class="form-control" id="add-firstName"
                              path="firstName" placeholder="First Name"/>
                    <sf:errors path="firstName" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-personLastName" class="col-md-4 control-label">Last Name:</label>
                    <div class="col-md-8">
                    <sf:input type="text" class="form-control" id="add-lastName"
                              path="lastName" placeholder="Last Name"/>
                    <sf:errors path="lastName" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-IsHero" class="col-md-4 control-label">Hero/Villian/Human:</label>
                    <div class="col-md-8">
                    <sf:input type="text" class="form-control" id="add-IsHero"
                              path="isHero" placeholder="Is Hero"/>
                    <sf:errors path="isHero" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-descriptionOfPerson" class="col-md-4 control-label">Description:</label>
                    <div class="col-md-8">
                    <sf:input type="text" class="form-control" id="add-descriptionOfPerson"
                              path="descriptionOfPerson" placeholder="Person Description"/>
                    <sf:errors path="descriptionOfPerson" cssclass="error"></sf:errors>
                        <!--           Hidden needs to be there for update but should be hidden from user so it cant be changed-->
                    <sf:hidden path="personId"/>
                </div>
            </div>

            <!--            DROP DOWN MENU FOR Superpowers-->
            <div class="form-group">
                <label for="add-personSuperpowers" class="col-md-4 control-label">Superpowers:</label>
                <select name="personSuperpowers" multiple ="true" class="col-md-4">
                    <c:forEach var="sp" items="${superpowerList}"> 
                        <option value ="${sp.superpowerId}">${sp.superpowerName}</option>
                    </c:forEach>
                </select>
            </div>
            <!--             DROP DOWN MENU FOR Organizations-->
            <div class="form-group">
                <label for="add-organizations" class="col-md-4 control-label"> Organizations: </label>
                <select name="personOrgs" multiple ="true" class="col-md-4">
                    <c:forEach var="org" items="${orgList}"> 
                        <option value ="${org.organizationId}">${org.organizationName}</option>
                    </c:forEach>
                </select>
            </div>
            <sf:hidden path="personId" />

            <!--                UPDATE BUTTON-->
            <div class="form-group">
                <div class="col-md-offset-4 col-md-8">
                    <input type="submit" class="btn btn-default" value="Update Person"/>
                </div>
            </div>
        </sf:form>  



        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>


</html>
