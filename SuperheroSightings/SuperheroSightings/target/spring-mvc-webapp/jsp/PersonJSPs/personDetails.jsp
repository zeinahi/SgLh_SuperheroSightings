<%-- 
    Document   : displayPersonDetails
    Created on : Jan 16, 2019, 2:40:39 PM
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
        <title>Person Details</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">     
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">    
    </head>
    <body>
        <hr>
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
                                <div class="col-md-8" firstTable>
                                    <h3 class = "Label">
                                        Organization Details
                                    </h3>


                                    <table id="orgsPersonTable" class="table table-hover">
                                        <br>
                                        <tr>
                                            <th width="25%">Organization Name</th>
                                            <th width="25%">Organization Type</th>
                                            <th width="25%">Organization Description</th>
                                            <th width="25%">Organization Address</th>
                                        </tr>

                                        <c:forEach var="currentOrganization" items="${orgDetailsForPerson}">
                                            <tr>
                                                <td>
                                                    <a href="organizationDetails?oganizantionId=${currentOrganization.organizationId}">
                                                        <c:out value="${currentOrganization.organizationName}"/>
                                                    </a>
                                                </td>
                                                <td>
                                                    <c:out value="${currentOrganization.isItAHeroOrganization}"/>
                                                </td>
                                                <td>
                                                    <c:out value="${currentOrganization.organizationDescription}"/>
                                                </td>
                                                <td>
                                                    <c:out value="${currentOrganization.organizationCountry}"/>
                                                    <c:out value="${currentOrganization.organizationState}"/>
                                                    <c:out value="${currentOrganization.organizationCity}"/>
                                                    <c:out value="${currentOrganization.organizationStreet}"/>
                                                    <c:out value="${currentOrganization.organizationZipcode}"/>
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
                                                        Superpowers
                                                    </h3>

                                                    <table id="superpowersInPersonTable" class="table table-hover">
                                                        <tr>
                                                            <th width="50%">Superpower Name</th>
                                                            <th width="50%">Superpower Description</th>
                                                        </tr>
                                                        <br>
                                                        <c:forEach var="currentSuperpower" items="${superpowerDetailsForPerson}">
                                                            <tr>
                                                                <td>
                                                                    <a href="superpowerDetails?superpowerId=${currentSuperpower.superpowerId}">
                                                                        <c:out value ="${currentSuperpower.superpowerName}"/>
                                                                    </a>
                                                                </td>
                                                                <td>
                                                                    <c:out value ="${currentSuperpower.superpowerDescription}"/>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>    

                                                </div>

                                            </div>
                                        </div><!--
-->                                    </div>
                                </div><!--

                                <div class="col-md-2">
                                </div>
-->                            </div>
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
