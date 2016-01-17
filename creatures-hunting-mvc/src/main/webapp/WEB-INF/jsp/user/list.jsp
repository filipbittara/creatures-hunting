<%-- 
    Document   : list.jsp
    Created on : 10.12.2015, 18:49:48
    Author     : Ondrej Klein
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="Registered users">
<jsp:attribute name="body">
    
        <table class="table">
        <caption>Users</caption>
        <thead>
        <tr>
            <th>Id</th>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><c:out value="${user.username}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td><my:a href="/user/detail/${user.id}" role="button" class="btn btn-success">Details</my:a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
        
</jsp:attribute>
</my:template>
