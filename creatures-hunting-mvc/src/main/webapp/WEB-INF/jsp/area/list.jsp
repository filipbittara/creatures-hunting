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

<my:template title="List of areas">
<jsp:attribute name="body">
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
            <tr>
                <td data-toggle="collapse" data-target="#${area.id}detail" class="accordion-toggle clickable">
                ${area.name}</td>
                <td data-toggle="collapse" data-target="#${area.id}detail" class="accordion-toggle clickable">
                ${area.latitude}</td>
                <td data-toggle="collapse" data-target="#${area.id}detail" class="accordion-toggle clickable">
                ${area.longitude}</td>
            </tr>
            <tr class="zeroPadding">
                <td colspan="4" class="hiddenRow" style="padding: 0; border-top-width: 0">
                    <div class="accordian-body collapse" id="${area.id}detail">
                    <div class="row">
          
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
                              <td>${area.latitude}</td>
                            </tr>
                            <tr>
                              <th>Longitude</th>
                              <td>${area.longitude}</td>
                            </tr>
                        </table>
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