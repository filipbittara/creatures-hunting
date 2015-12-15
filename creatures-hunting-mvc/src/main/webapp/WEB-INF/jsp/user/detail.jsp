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

<my:template title="${user.username} - User">
<jsp:attribute name="body">
    <div class="container">
      <div class="row">
        <div class="col-md-6">
          <img class="image-responsive" src="${pageContext.request.contextPath}/user/userImage/${user.id}"/>
        </div>
        <div class="col-md-6">
          <table class="table">
            <caption>User information</caption>
            <tr>
              <th>Id</th>
              <td>${user.id}</td>
            </tr>
            <tr>
              <th>Username</th>
              <td>${user.username}</td>
            </tr>
            <tr>
              <th>Email</th>
              <td>${user.email}</td>
            </tr>
            <tr>
              <th>Role</th>
              <td>${user.role}</td>
            </tr>
          </table>
        </div>
      </div>
    </div>
</jsp:attribute>
</my:template>
