<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="Edit weapon">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/weapon/admin/update/${weaponUpdate.id}"
               modelAttribute="weaponUpdate" cssClass="form-horizontal" enctype="multipart/form-data">
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="gunReach" cssClass="col-sm-2 control-label">Gun reach</form:label>
            <div class="col-sm-10">
                <form:input path="gunReach" cssClass="form-control"/>
                <form:errors path="gunReach" cssClass="help-block"/>
            </div>
            </div>
                    <div class="form-group ${name_error?'has-error':''}">
            <form:label path="ammunition" cssClass="col-sm-2 control-label">Ammunition</form:label>
            <div class="col-sm-10">
                <form:select path="ammunition" cssClass="form-control">
                    <c:forEach items="${types}" var="type">
                        <form:option value="${type}">${type}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="ammunition" cssClass="help-block"/>
            </div>
            </div>
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="multipartImage" cssClass="col-sm-2 control-label">Image</form:label>
            <div class="col-sm-10">
                <form:input path="multipartImage" cssClass="form-control" type="file"/>
                <form:errors path="multipartImage" cssClass="help-block"/>
            </div>
        </div>
      
        <button class="btn btn-primary" type="submit">Update weapon</button>
    </form:form>

</jsp:attribute>
</my:template>