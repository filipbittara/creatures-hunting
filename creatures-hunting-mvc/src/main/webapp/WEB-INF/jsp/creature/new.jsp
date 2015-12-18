<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="New creature">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/creature/admin/create"
               modelAttribute="creatureCreate" cssClass="form-horizontal">
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
            <div class="col-sm-10">
                <form:select path="type" cssClass="form-control">
                    <c:forEach items="${types}" var="type">
                        <form:option value="${type}">${type}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="type" cssClass="error"/>
            </div>
        </div>
                    <div class="form-group ${name_error?'has-error':''}">
            <form:label path="height" cssClass="col-sm-2 control-label">Height</form:label>
            <div class="col-sm-10">
                <form:input path="height" cssClass="form-control"/>
                <form:errors path="height" cssClass="help-block"/>
            </div>
        </div>
                    <div class="form-group ${name_error?'has-error':''}">
            <form:label path="weight" cssClass="col-sm-2 control-label">Weight</form:label>
            <div class="col-sm-10">
                <form:input path="weight" cssClass="form-control"/>
                <form:errors path="weight" cssClass="help-block"/>
            </div>
        </div>
                    <div class="form-group ${name_error?'has-error':''}">
            <form:label path="agility" cssClass="col-sm-2 control-label">Agility</form:label>
            <div class="col-sm-10">
                <form:input path="agility" cssClass="form-control"/>
                <form:errors path="agility" cssClass="help-block"/>
            </div>
        </div>
                    <div class="form-group ${name_error?'has-error':''}">
            <form:label path="ferocity" cssClass="col-sm-2 control-label">Ferocity</form:label>
            <div class="col-sm-10">
                <form:input path="ferocity" cssClass="form-control"/>
                <form:errors path="ferocity" cssClass="help-block"/>
            </div>
        </div>
                    <div class="form-group ${name_error?'has-error':''}">
            <form:label path="weakness" cssClass="col-sm-2 control-label">Weakness</form:label>
            <div class="col-sm-10">
                <form:input path="weakness" cssClass="form-control"/>
                <form:errors path="weakness" cssClass="help-block"/>
            </div>
        </div>
      
        <button class="btn btn-primary" type="submit">Create creature</button>
    </form:form>

</jsp:attribute>
</my:template>