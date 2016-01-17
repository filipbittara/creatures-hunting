<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="New weapon">
<jsp:attribute name="body">
    <script type="text/javascript">
        $(document).ready(function() {
            $('input#name').on("change paste keyup", function() {
                var empty = false;
                $('input#name').each(function() {
                    if ($(this).val().length == 0) {
                        empty = true;
                    }
                });

                if (empty) {
                    $('button[type="submit"]').attr('disabled', 'disabled');
                    $('#enter_name').css('display', 'block');
                } else {
                    $('button[type="submit"]').removeAttr('disabled');
                    $('#enter_name').css('display', 'none');
                }
            });

            $('input#gunReach').on("change paste keyup", function() {
                if ($('input#gunReach').val().length == 0
                        || parseFloat($('input#gunReach').val()) < 0
                        || $.isNumeric($('input#gunReach').val()) == false) {
                    $('#reach_err').css('display', 'block');
                } else {
                    $('#reach_err').css('display', 'none');
                }
                console.log($('span.input-err:visible').length);
                if ($('span.input-err:visible').length == 0) {
                    $('button[type="submit"]').removeAttr('disabled');
                } else {
                    $('button[type="submit"]').attr('disabled', 'disabled');
                }
            });
        });
    </script>
    <form:form method="post" action="${pageContext.request.contextPath}/weapon/admin/create"
               modelAttribute="weaponCreate" cssClass="form-horizontal" enctype="multipart/form-data">
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
      
        <button class="btn btn-primary" disabled="disabled" type="submit">Create weapon</button>
        <span style="color: red; display: block" id="enter_name" class="input-err">You must <strong>name</strong> the weapon.</span>
        <span style="color: red; display: block" id="reach_err" class="input-err">The <strong>reach</strong> of the weapon must be >=0.</span>
    </form:form>

</jsp:attribute>
</my:template>