<%-- 
    Document   : displayOrganizationDetails
    Created on : Jan 16, 2019, 2:39:42 PM
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
        <title>Organization Details </title>

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

        <div class="container" >    
            <h1>Organization Details</h1>
            <hr/>


            <h3> Person </h3>
            <div class="col-md-8">     
                <table id="orgsPersonTable" class="table table-hover">

                    <tr>
                        <th width="25%">Person Name</th>
                        <th width="25%">Is this a Superhero?</th>
                        <th width="25%">Superpowers</th>
                        <th width="25%">Person Description</th>
                    </tr>

                    <c:forEach var = "currentPerson" items = "${personsInOrg}">
                        <tr>

                            <td>
                                <a href="personDetails?personId=${currentPerson.personId}">
                                    <c:out value ="${currentPerson.firstName}  ${currentPerson.lastName}"/>
                                </a>
                            </td>

                            <td>
                                <c:out value="${currentPerson.isHero}"/>
                            </td>

                            <td>
                                <c:out value="${currentPerson.descriptionOfPerson}"/>
                            </td>  

                        </tr>   
                    </c:forEach>

                </table>

            </div>



            <div class="col-md-8">
                <h3> Users </h3>

                <table id="orgsUserTable" class="table table-hover">


                    <tr>
                        <th width="25%">Authority</th>
                        <th width="25%">Username</th>
                        <th width="25%">Name</th>
                        <th width="25%">Email</th>
                    </tr>        


                    <c:forEach var="currentUser" items="${usersInOrg}">


                        <tr>
                            <td>
                                <a href="userDetails?userName=${currentUser.userName}">
                                    <c:out value ="${currentUser.userName}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value ="${currentUser.firstName} ${currentUser.lastName}"/>
                            </td>
                            <td>
                                <c:out value ="${currentUser.email}"/>
                            </td>            

                        </tr>

                    </c:forEach>  

                </table>     

            </div>

        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>                 


    </body>
</html>