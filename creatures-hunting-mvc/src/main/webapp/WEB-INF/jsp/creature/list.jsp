<%-- 
    Document   : list
    Created on : 10.12.2015, 18:50:04
    Author     : David Kizivat
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="List of creatures">
<jsp:attribute name="body">
    <table class="table">
        <caption>Creatures</caption>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Type</th>
            <th>Weakness</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${creatures}" var="creature">
            <tr>
                <td>${creature.id}</td>
                <td>${creature.name}</td>
                <td>${creature.type}</td>
                <td>${creature.weakness}</td>
                <td><my:a href="/creature/detail/${creature.id}" role="button" class="btn btn-success">Details</my:a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:template>