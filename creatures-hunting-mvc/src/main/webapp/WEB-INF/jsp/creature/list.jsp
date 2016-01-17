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


<my:template title="List of creatures">
    <jsp:attribute name="body">

        <script  type="text/javascript">
            function submit_filter() {
                if (event.keyCode == 13) {
                    location.href = '${pageContext.request.contextPath}' + '/creature/list/filter/' + filter_page.value
                }
            }
        </script>
        <a data-toggle="collapse" data-target="#creaturesInCircle" class="accordion-toggle clickable">Do you want to know, what creatures are around you?</a><br />

        <div class="accordian-body collapse" id="creaturesInCircle">

            <div class="input-group col-md-6">
                <div>
                    <label cssClass="col-sm-2 control-label">Longitude</label>
                    <input type="text" id="x" class="form-control" value="${x}"/>
                </div>
                <div>
                    <label cssClass="col-sm-2 control-label">Latitude</label>
                    <input type="text" id="y" class="form-control" value="${y}"/>
                </div>
                <div>
                    <label cssClass="col-sm-2 control-label">Radius</label>
                    <input type="text" id="radius" class="form-control" value="${radius}"/>
                </div>
                <input type="button" value="Show" class="btn btn-primary" onclick="location.href = '${pageContext.request.contextPath}' + '/creature/circle/' + x.value + '/' + y.value + '/' + radius.value"/>
            </div>

        </div>
        <br/>
        <div class="input-group col-md-6">
            <input type="text" id="filter_page" onkeydown="submit_filter()" class="form-control" value="${filter}"/>
            <span class="input-group-btn">
                <input type="button" value="Filter" class="btn btn-primary" onclick="location.href = '${pageContext.request.contextPath}' + '/creature/list/filter/' + filter_page.value"/>
            </span>
        </div>


        <c:if test="${not empty authenticatedAdmin}">
            <my:a href="/creature/admin/new" class="btn btn-primary">
                Add new creature
            </my:a>
        </c:if>

        <table class="table">
            <thead>
                <tr>
                    <!--<th>Id</th>-->
                    <th>Name</th>
                    <th>Type</th>
                    <th>Weakness</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${creatures}" var="creature">
                    <tr>
                        <!--<td>${creature.id}</td>-->
                        <td data-toggle="collapse" data-target="#${creature.id}detail" class="accordion-toggle clickable">
                            ${creature.name}</td>
                        <td data-toggle="collapse" data-target="#${creature.id}detail" class="accordion-toggle clickable">
                            ${creature.type}</td>
                        <td data-toggle="collapse" data-target="#${creature.id}detail" class="accordion-toggle clickable">
                            ${creature.weakness}</td>
                        <td> 
                            <c:if test="${not empty authenticatedAdmin}">
                                <form style="display: inline;" method="post" action="${pageContext.request.contextPath}/creature/admin/delete/${creature.id}">                      
                                    <c:choose>
                                        <c:when test="${creatureWeapons[creature.id] == 'nothing' && creatureAreas[creature.id] == 'nowhere'}">
                                            <button type="submit" class="btn btn-primary">Delete</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" disabled class="btn btn-primary">Delete</button>                            
                                        </c:otherwise>
                                    </c:choose>  
                                </form>
                                <form style="display: inline;" method="get" action="${pageContext.request.contextPath}/creature/admin/update/${creature.id}">                      
                                    <button type="submit" class="btn btn-primary">Update</button>  
                                </form>
                            </c:if>
                        </td>
                        <!--<td><button class="btn btn-success">Details</span></button></td>-->
                    </tr>
                    <tr class="zeroPadding">
                        <td colspan="4" class="hiddenRow" style="padding: 0; border-top-width: 0">
                            <c:choose>
                                <c:when test="${creature.id == shouldBeOpen}">
                                    <div class="accordian-body collapse in" id="${creature.id}detail">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="accordian-body collapse" id="${creature.id}detail">
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <img width="300" height="300" src="${pageContext.request.contextPath}/creature/creatureImage/${creature.id}"/>
                                        </div>
                                        <div class="col-md-6">
                                            <table class="table">
                                                <caption>Creature properties</caption>
                                                <!--<tr>
                                                    <th>Id</th>
                                                    <td>${creature.id}</td>
                                                </tr>-->
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
                                                    <td>${creature.height} cm</td>
                                                </tr>
                                                <tr>
                                                    <th>Weight</th>
                                                    <td>${creature.weight} kg</td>
                                                </tr>
                                                <tr>
                                                    <th>Agility</th>
                                                    <td>${creature.agility}<b>/10</b></td>
                                                </tr>
                                                <tr>
                                                    <th>Ferocity</th>
                                                    <td>${creature.ferocity}<b>/10</b></td>
                                                </tr>
                                                <tr>
                                                    <th>Weakness</th>
                                                    <td>${creature.weakness}</td>
                                                </tr>
                                            </table>
                                        </div>
                                        <div class="col-md-6">
                                            Creature can be harmed by: ${creatureWeapons[creature.id]}. <br/>
                                            <a data-toggle="collapse" data-target="#${creature.id}addWeapon" class="accordion-toggle clickable">Have you used another weapon and survived?</a><br/>
                                            <div class="accordian-body collapse" id="${creature.id}addWeapon">
                                                <c:set var="showed" value="0"/>
                                                <c:forEach items="${weapons}" var="weapon">
                                                    <c:if test="${not fn:contains(creatureWeapons[creature.id], weapon.name)}">
                                                        <my:a href="/creature/addWeapon/${weapon.id}/to/${creature.id}" class="text-success">I've used ${weapon.name}.</my:a><br/>
                                                        <c:set var="showed" value="${showed + 1}"/>
                                                    </c:if>

                                                </c:forEach>
                                                <c:if test="${showed == 0}">
                                                    Sorry, no other weapon available.
                                                </c:if>
                                                <c:set var="showed" value="0"/>        
                                            </div>

                                            <a data-toggle="collapse" data-target="#${creature.id}removeWeapon" class="accordion-toggle clickable">Do you think one of the listed weapons is ineffective against this monster?</a>
                                            <div class="accordian-body collapse" id="${creature.id}removeWeapon">
                                                <c:set var="showed" value="0"/>
                                                <c:forEach items="${weapons}" var="weapon">
                                                    <c:if test="${fn:contains(creatureWeapons[creature.id], weapon.name)}">
                                                        <my:a href="/creature/removeWeapon/${weapon.id}/from/${creature.id}" class="text-success">Yes, ${weapon.name} is worthless.</my:a><br/>
                                                        <c:set var="showed" value="${showed + 1}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${showed == 0}">
                                                    Sorry, no weapon listed.
                                                </c:if>
                                                <c:set var="showed" value="0"/>        
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            Creature was seen in: ${creatureAreas[creature.id]}. <br/>

                                            <a data-toggle="collapse" data-target="#${creature.id}addArea" class="accordion-toggle clickable">Have you seen this monster elsewhere?</a><br/>
                                            <div class="accordian-body collapse" id="${creature.id}addArea">
                                                <c:set var="showed" value="0"/>
                                                <c:forEach items="${areas}" var="area">
                                                    <c:if test="${not fn:contains(creatureAreas[creature.id], area.name)}">
                                                        <my:a href="/creature/addArea/${area.id}/to/${creature.id}" class="text-success">I've seen it in ${area.name}.</my:a><br/>
                                                        <c:set var="showed" value="${showed + 1}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${showed == 0}">
                                                    Sorry, no other area available.
                                                </c:if>
                                            </div>

                                            <a data-toggle="collapse" data-target="#${creature.id}removeArea" class="accordion-toggle clickable">Do you think that one of the listed areas is wrong?</a>
                                            <div class="accordian-body collapse" id="${creature.id}removeArea">
                                                <c:set var="showed" value="0"/>
                                                <c:forEach items="${areas}" var="area">
                                                    <c:if test="${fn:contains(creatureAreas[creature.id], area.name)}">
                                                        <my:a href="/creature/removeArea/${area.id}/from/${creature.id}" class="text-success">Yes, there is no ${creature.name} in ${area.name}.</my:a><br/>
                                                        <c:set var="showed" value="${showed + 1}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${showed == 0}">
                                                    Sorry, no area listed.
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