<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="New area">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/area/admin/create"
               modelAttribute="areaCreate" cssClass="form-horizontal" enctype="multipart/form-data">
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="description" cssClass="col-sm-2 control-label">Description</form:label>
            <div class="col-sm-10">
                <form:input path="description" cssClass="form-control"/>
                <form:errors path="description" cssClass="help-block"/>
            </div>
            </div>
            <div class="form-group ${name_error?'has-error':''}">
            <form:label path="latitude" cssClass="col-sm-2 control-label">Latitude</form:label>
            <div class="col-sm-10">
                <form:input path="latitude" cssClass="form-control"/>
                <form:errors path="latitude" cssClass="help-block"/>
            </div>
            </div>
            <div class="form-group ${name_error?'has-error':''}">
            <form:label path="longitude" cssClass="col-sm-2 control-label">Longitude</form:label>
            <div class="col-sm-10">
                <form:input path="longitude" cssClass="form-control"/>
                <form:errors path="longitude" cssClass="help-block"/>
            </div>
            </div>
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="multipartImage" cssClass="col-sm-2 control-label">Image</form:label>
            <div class="col-sm-10">
                <form:input path="multipartImage" cssClass="form-control" type="file"/>
                <form:errors path="multipartImage" cssClass="help-block"/>
            </div>
        </div>
      
        <button class="btn btn-primary" type="submit">Create area</button>
    </form:form>

</jsp:attribute>
</my:template>