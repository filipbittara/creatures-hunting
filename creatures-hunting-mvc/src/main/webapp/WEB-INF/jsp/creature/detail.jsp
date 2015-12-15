<%-- 
    Document   : detail
    Created on : 13.12.2015, 16:10:39
    Author     : David Kizivat
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="${creature.name} - Creature">
<jsp:attribute name="body">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <img class="image-responsive" src="${pageContext.request.contextPath}/creature/creatureImage/${creature.id}"/>
            </div>
            <div class="col-md-6">
                <table class="table">
                    <caption>Creature properties</caption>
                    <tr>
                        <th>Id</th>
                        <td>${creature.id}</td>
                    </tr>
                    <tr>
                        <th>Name</th>
                        <td>${creature.name}</td>
                    </tr>
                    <tr>
                        <th>Type</th>
                        <td>${creature.type}</td>
                    </tr>
                    <tr>
                        <th>Height</th>
                        <td>${creature.height}</td>
                    </tr>
                    <tr>
                        <th>Weight</th>
                        <td>${creature.weight}</td>
                    </tr>
                    <tr>
                        <th>Agility</th>
                        <td>${creature.agility}</td>
                    </tr>
                    <tr>
                        <th>Ferocity</th>
                        <td>${creature.ferocity}</td>
                    </tr>
                    <tr>
                        <th>Weakness</th>
                        <td>${creature.weakness}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</jsp:attribute>
</my:template>
