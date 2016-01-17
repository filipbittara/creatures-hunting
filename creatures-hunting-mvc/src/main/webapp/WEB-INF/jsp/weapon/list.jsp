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


<my:template title="Weapons">
    <jsp:attribute name="body">


        <script  type="text/javascript">
            function submit_filter() {
                if (event.keyCode == 13) {
                    location.href = '${pageContext.request.contextPath}' + '/weapon/list/filter/' + filter_page.value
                }
            }

            function areaWeaponFormSubmit() {
                document.getElementById("areasw").submit();
            }

            $(function () {
                $(document).on('click', '.btn-add', function (e) {
                    e.preventDefault();
                    var controlForm = $('.controls form:first'), currentEntry = $(this).parents('.entry:first'), newEntry = $(currentEntry.clone()).appendTo(controlForm);
                    newEntry.find('input').val('');
                    controlForm.find('.entry:not(:last) .btn-add').removeClass('btn-add').addClass('btn-remove').removeClass('btn-success').addClass('btn-danger').html('<span class="glyphicon glyphicon-minus"></span>');
                }).on('click', '.btn-remove', function (e) {
                    $(this).parents('.entry:first').remove();
                    e.preventDefault();
                    return false;
                });
            });
        </script>

        <a data-toggle="collapse" data-target="#neededWeapons" class="accordion-toggle clickable">What you like to know what weapons to bring for crossing certain areas?</a><br />
        <div class="accordian-body collapse" id="neededWeapons">
            <div class="container">
                <div class="row"> 
                    <div class="control-group" id="fields"> 
                        <div class="controls"> 
                            <form id="areasw" role="form" action="${pageContext.request.contextPath}/weapon/areas" autocomplete="off" method="post"> 
                                <label cssClass="col-sm-2 control-label">Fill in areas</label>
                                <div class="entry input-group col-xs-3">
                                    <select class="form-control" name="field">
                                        <option></option>
                                        <c:forEach var="area" items="${areas}">
                                            <option>${area.name}</option>
                                        </c:forEach>
                                    </select>
                                    <!--<input class="form-control" name="field" type="text" placeholder="Type something" />--> 
                                    <span class="input-group-btn"> 
                                        <button class="btn btn-success btn-add" type="button"> 
                                            <span class="glyphicon glyphicon-plus"></span> 
                                        </button> 
                                    </span> 
                                </div>

                            </form>

                            <button type="submit" value=" Send" class="btn btn-success" id="submit" onclick="areaWeaponFormSubmit()" />Submit</button> 

                            <br> 
                            <small>Press <span class="glyphicon glyphicon-plus gs"></span> to add another form field</small> 
                        </div> 
                    </div> 
                </div> 

            </div>
        </div>
        <br/>

        <div class="input-group col-md-6">
            <input type="text" id="filter_page" onkeydown="submit_filter()" class="form-control" value="${filter}"/>
            <span class="input-group-btn">
                <input type="button" value="Filter" class="btn btn-primary" onclick="location.href = '${pageContext.request.contextPath}' + '/weapon/list/filter/' + filter_page.value"/>
            </span>
        </div>


        <c:if test="${not empty authenticatedAdmin}">
            <my:a href="/weapon/admin/new" class="btn btn-primary">
                Add new weapon
            </my:a>
        </c:if>
        <table class="table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Gun Reach</th>
                    <th>Ammunition</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${weapons}" var="weapon">
                    <tr class="short-info-row">
                        <td data-toggle="collapse" data-target="#${weapon.id}detail" class="accordion-toggle clickable">
                            ${weapon.name}</td>
                        <td data-toggle="collapse" data-target="#${weapon.id}detail" class="accordion-toggle clickable">
                            ${weapon.gunReach}</td>
                        <td data-toggle="collapse" data-target="#${weapon.id}detail" class="accordion-toggle clickable">
                            ${weapon.ammunition}</td>
                        <td>    
                            <c:if test="${not empty authenticatedAdmin}">
                                <form style="display: inline;" method="post" action="${pageContext.request.contextPath}/weapon/admin/delete/${weapon.id}">                      
                                    <c:choose>
                                        <c:when test="${creatureWeapon[weapon.id] == 'no creature'}">
                                            <button type="submit" class="btn btn-primary">Delete</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" title="Cannot delete an item that has something other associated with it..." disabled class="btn btn-primary">Delete</button>
                                        </c:otherwise>
                                    </c:choose> 
                                </form>
                                <form style="display: inline;" method="get" action="${pageContext.request.contextPath}/weapon/admin/update/${weapon.id}">                      
                                    <button type="submit" class="btn btn-primary">Update</button>  
                                </form>
                            </c:if>
                        </td>
                    </tr>

                    <tr class="zeroPadding">
                        <td colspan="4" class="hiddenRow" style="padding: 0; border-top-width: 0">
                            <c:choose>
                                <c:when test="${weapon.id == shouldBeOpen}">
                                    <div class="accordian-body collapse in" id="${weapon.id}detail">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="accordian-body collapse" id="${weapon.id}detail">
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <img width="300" height="300" src="${pageContext.request.contextPath}/weapon/weaponImage/${weapon.id}"/>
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

                                            <a data-toggle="collapse" data-target="#${weapon.id}addWeapon" class="accordion-toggle clickable">Have you injured any other creature with this weapon?</a><br/>
                                            <div class="accordian-body collapse" id="${weapon.id}addWeapon">
                                                <c:set var="showed" value="0"/>
                                                <c:forEach items="${creatures}" var="creature">
                                                    <c:if test="${not fn:contains(creatureWeapon[weapon.id], creature.name)}">
                                                        <my:a href="/weapon/addCreature/${creature.id}/to/${weapon.id}" class="text-success">I've hurt ${creature.name}.</my:a><br/>
                                                        <c:set var="showed" value="${showed + 1}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${showed == 0}">
                                                    Sorry, no other creature available.
                                                </c:if>
                                            </div>

                                            <a data-toggle="collapse" data-target="#${weapon.id}removeWeapon" class="accordion-toggle clickable">Was this weapon ineffective against any listed creature?</a>
                                            <div class="accordian-body collapse" id="${weapon.id}removeWeapon">
                                                <c:set var="showed" value="0"/>
                                                <c:forEach items="${creatures}" var="creature">
                                                    <c:if test="${fn:contains(creatureWeapon[weapon.id], creature.name)}">
                                                        <my:a href="/weapon/removeCreature/${creature.id}/from/${weapon.id}" class="text-success">Yes, it is ineffective against ${creature.name}.</my:a><br/>
                                                        <c:set var="showed" value="${showed + 1}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${showed == 0}">
                                                    Sorry, no creatures listed.
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
