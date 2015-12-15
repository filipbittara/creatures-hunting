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

<my:template title="${area.name} - Area">
<jsp:attribute name="body">
    <div class="container">
      <div class="row">
          <table class="table">
            <caption>Area properties</caption>
            <tr>
              <th>Id</th>
              <td>${area.id}</td>
            </tr>
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
</jsp:attribute>
</my:template>
