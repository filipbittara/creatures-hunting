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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<my:template title="List of weapons">
<jsp:attribute name="body">
    <table class="table">
        <caption>Weapons</caption>
        <thead>
            <tr>
                <th>Name</th>
                <th>Gun Reach</th>
                <th>Ammunition</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${weapons}" var="weapon">
                <tr>
                    <td data-toggle="collapse" data-target="#${weapon.id}detail" class="accordion-toggle clickable">
                ${weapon.name}</td>
                    <td data-toggle="collapse" data-target="#${weapon.id}detail" class="accordion-toggle clickable">
                ${weapon.gunReach}</td>
                    <td data-toggle="collapse" data-target="#${weapon.id}detail" class="accordion-toggle clickable">
                ${weapon.ammunition}</td>
                </tr>
            <tr class="zeroPadding">
                <td colspan="4" class="hiddenRow" style="padding: 0; border-top-width: 0">
                    <div class="accordian-body collapse" id="${weapon.id}detail">
                            <div class="row">
                                <div class="col-md-6">
                                    <img class="image-responsive" src="${pageContext.request.contextPath}/weapon/weaponImage/${weapon.id}"/>
                                </div>
                                <div class="col-md-6">
                                    <table class="table">
                                        <caption>Weapon properties</caption>
                                        <tr>
                                            <th>Name</th>
                                            <td>${weapon.name}</td>
                                        </tr>
                                        <tr>
                                            <th>Gun Reach</th>
                                            <td>${weapon.gunReach}</td>
                                        </tr>
                                        <tr>
                                            <th>Ammunition</th>
                                            <td>${weapon.ammunition}</td>
                                        </tr>
                                    </table>
                                </div>
                                        <div class="col-md-6">
                                Creatures that can be injured with this weapon: ${creatureWeapon[weapon.id]}. <br/>
                                
                                <a data-toggle="collapse" data-target="#${weapon.id}addWeapon" class="accordion-toggle clickable">Have you injured any other creature with this weapon?</a>
                                <div class="accordian-body collapse" id="${weapon.id}addWeapon">
                                <c:set var="showed" value="0"/>
                                <c:forEach items="${creatures}" var="creature">
                                    <c:if test="${not fn:contains(creatureWeapon[weapon.id], creature.name)}">
                                        <my:a href="/weapon/addCreature/${creature.id}/to/${weapon.id}" class="text-success">I've hurt ${creature.name}.</my:a><br/>
                                        <c:set var="showed" value="${showed + 1}"/>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${showed == 0}">
                                        Sorry no other creature available.
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
