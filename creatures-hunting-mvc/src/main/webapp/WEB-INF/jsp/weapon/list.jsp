<%-- 
    Document   : list
    Created on : 10.12.2015, 18:50:04
    Author     : Ondrej Klein
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="List of weapons">
<jsp:attribute name="body">
    <table class="table">
        <caption>Weapons</caption>
        <thead>
            <tr>
                <th>Id</th>>
                <th>Name</th>
                <th>Gun Reach</th>
                <th>Ammunition</th>
            </tr>>
        </thead>
        <tbody>
            <c:forEach items="${weapons}" var="weapon">
                <tr>
                    <td>${weapon.id}</td>
                    <td>${weapon.name}</td>
                    <td>${weapon.gunReach}</td>
                    <td>${weapon.ammunition}</td>
                    <td><my:a href="/weapon/detail/${weapon.id}" role="button" class="btn btn-success">Detail</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:template>
