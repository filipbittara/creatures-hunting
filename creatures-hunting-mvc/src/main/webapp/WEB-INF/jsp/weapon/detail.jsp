<%-- 
    Document   : detail
    Created on : 13.12.2015, 16:10:39
    Author     : Ondrej Klein
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Weapon Detail">
<jsp:attribute name="body">
    <div class=""container>
        <div class="row">
            <div class="col-md-6">
                <img class="image-responsive" src="${pageContext.request.contextPath}/weapon/weaponImage/${weapon.id}"/>
            </div>
            <div class="col-md-6">
                <table class="table">
                    <caption>Weapon properties</caption>
                    <tr>
                        <th>Id</th>
                        <td>${weapon.id}</td>
                    </tr>
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
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>
