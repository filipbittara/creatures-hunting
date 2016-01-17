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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<my:template title="Areas">
    <jsp:attribute name="body">

        <script  type="text/javascript">
            function submit_filter() {
                if (event.keyCode == 13) {
                    location.href = '${pageContext.request.contextPath}' + '/area/list/filter/' + filter_page.value
                }
            }
        </script>
        <div class="input-group col-md-6">
            <input type="text" id="filter_page" onkeydown="submit_filter()" class="form-control" value="${filter}"/>
            <span class="input-group-btn">
                <input type="button" value="Filter" class="btn btn-primary" onclick="location.href = '${pageContext.request.contextPath}' + '/area/list/filter/' + filter_page.value"/>
            </span>
        </div>

        <c:if test="${not empty authenticatedAdmin}">
            <my:a href="/area/admin/new" class="btn btn-primary">
                Add new area
            </my:a>
        </c:if>
        <table class="table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Latitude</th>
                    <th>Longitude</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${areas}" var="area">
                    <tr class="short-info-row">
                        <td data-toggle="collapse" data-target="#${area.id}detail" class="accordion-toggle clickable">
                            ${area.name}</td>
                        <td data-toggle="collapse" data-target="#${area.id}detail" class="accordion-toggle clickable">
                            <c:choose>
                                <c:when test="${area.latitude < 0}">
                                    ${-area.latitude} S
                                </c:when>
                                <c:otherwise>
                                    ${area.latitude} N
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td data-toggle="collapse" data-target="#${area.id}detail" class="accordion-toggle clickable">
                            <c:choose>
                                <c:when test="${area.longitude < 0}">
                                    ${-area.longitude} W
                                </c:when>
                                <c:otherwise>
                                    ${area.longitude} E
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>    
                            <c:if test="${not empty authenticatedAdmin}">
                                <form style="display: inline;" method="post" action="${pageContext.request.contextPath}/area/admin/delete/${area.id}">                      
                                    <c:choose>
                                        <c:when test="${creatureAreas[area.id] == 'no creature'}">
                                            <button type="submit" class="btn btn-primary">Delete</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" title="Cannot delete an item that has something other associated with it..." disabled class="btn btn-primary">Delete</button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                                <form style="display: inline;" method="get" action="${pageContext.request.contextPath}/area/admin/update/${area.id}">                      
                                    <button type="submit" class="btn btn-primary">Update</button>  
                                </form>
                            </c:if>
                        </td>
                    </tr>
                    <tr class="zeroPadding">
                        <td colspan="4" class="hiddenRow" style="padding: 0; border-top-width: 0">
                            <c:choose>
                                <c:when test="${area.id == shouldBeOpen}">
                                    <div class="accordian-body collapse in" id="${area.id}detail">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="accordian-body collapse" id="${area.id}detail">
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <img width="300" height="300" src="${pageContext.request.contextPath}/area/areaImage/${area.id}"/>
                                        </div>

                                        <div class="col-md-6">

                                            <table class="table">
                                                <caption>Area properties</caption>
                                                <tr>
                                                    <th>Name</th>
                                                    <td>${area.name}</td>
                                                </tr>
                                                <tr>
                                                    <th>Description</th>
                                                    <td>${area.description}</td>
                                                </tr>
                                                <tr>
                                                    <th>Latitude</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${area.latitude < 0}">
                                                                ${-area.latitude} S
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${area.latitude} N
                                                            </c:otherwise>
                                                        </c:choose>

                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Longitude</th>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${area.longitude < 0}">
                                                                ${-area.longitude} W
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${area.longitude} E
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                        <div class="col-md-6">
                                            Creatures seen in this area: ${creatureAreas[area.id]}. <br/>

                                            <a data-toggle="collapse" data-target="#${area.id}addArea" class="accordion-toggle clickable">Have you seen any other monster here?</a><br/>
                                            <div class="accordian-body collapse" id="${area.id}addArea">
                                                <c:set var="showed" value="0"/>
                                                <c:forEach items="${creatures}" var="creature">
                                                    <c:if test="${not fn:contains(creatureAreas[area.id], creature.name)}">
                                                        <my:a href="/area/addCreature/${creature.id}/to/${area.id}" class="text-success">I've seen ${creature.name}.</my:a><br/>
                                                        <c:set var="showed" value="${showed + 1}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${showed == 0}">
                                                    Sorry, no other creature available.
                                                </c:if>
                                            </div>

                                            <a data-toggle="collapse" data-target="#${area.id}removeArea" class="accordion-toggle clickable">Do you think one of the listed monsters is not here?</a>
                                            <div class="accordian-body collapse" id="${area.id}removeArea">
                                                <c:set var="showed" value="0"/>
                                                <c:forEach items="${creatures}" var="creature">
                                                    <c:if test="${fn:contains(creatureAreas[area.id], creature.name)}">
                                                        <my:a href="/area/removeCreature/${creature.id}/from/${area.id}" class="text-success">Yes, ${creature.name} is not here.</my:a><br/>
                                                        <c:set var="showed" value="${showed + 1}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${showed == 0}">
                                                    Sorry, no creature listed.
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:template>