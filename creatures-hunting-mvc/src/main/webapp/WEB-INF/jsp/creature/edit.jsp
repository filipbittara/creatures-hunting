<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="Edit creature">
<jsp:attribute name="body">

    <script type="text/javascript">
        $(document).ready(function() {
            $('input#name').on("change paste keyup", function() {
                if ($('input#name').val().length == 0) {
                    $('#enter_name').css('display', 'block');
                } else {
                    $('#enter_name').css('display', 'none');
                }
                console.log($('span.input-err:visible').length);
                if ($('span.input-err:visible').length == 0) {
                    $('button[type="submit"]').removeAttr('disabled');
                } else {
                    $('button[type="submit"]').attr('disabled', 'disabled');
                }
            });

            $('input#height').on("change paste keyup", function() {
                if ($('input#height').val().length == 0
                        || parseFloat($('input#height').val()) <= 0
                        || $.isNumeric($('input#height').val()) == false) {
                    $('#height_err').css('display', 'block');
                } else {
                    $('#height_err').css('display', 'none');
                }
                console.log($('span.input-err:visible').length);
                if ($('span.input-err:visible').length == 0) {
                    $('button[type="submit"]').removeAttr('disabled');
                } else {
                    $('button[type="submit"]').attr('disabled', 'disabled');
                }
            });

            $('input#weight').on("change paste keyup", function() {
                if ($('input#weight').val().length == 0
                        || parseFloat($('input#weight').val()) <= 0
                        || $.isNumeric($('input#weight').val()) == false) {
                    $('#weight_err').css('display', 'block');
                } else {
                    $('#weight_err').css('display', 'none');
                }
                console.log($('span.input-err:visible').length);
                if ($('span.input-err:visible').length == 0) {
                    $('button[type="submit"]').removeAttr('disabled');
                } else {
                    $('button[type="submit"]').attr('disabled', 'disabled');
                }
            });

            $('input#agility').on("change paste keyup", function() {
                if ($('input#agility').val().length == 0
                        || parseInt($('input#agility').val()) < 0
                        || parseInt($('input#agility').val()) > 10
                        || $.isNumeric($('input#agility').val()) == false) {
                    $('#agi_err').css('display', 'block');
                } else {
                    $('#agi_err').css('display', 'none');
                }
                console.log($('span.input-err:visible').length);
                if ($('span.input-err:visible').length == 0) {
                    $('button[type="submit"]').removeAttr('disabled');
                } else {
                    $('button[type="submit"]').attr('disabled', 'disabled');
                }
            });

            $('input#ferocity').on("change paste keyup", function() {
                if ($('input#ferocity').val().length == 0
                        || parseInt($('input#ferocity').val()) < 0
                        || parseInt($('input#ferocity').val()) > 10
                        || $.isNumeric($('input#ferocity').val()) == false) {
                    $('#fero_err').css('display', 'block');
                } else {
                    $('#fero_err').css('display', 'none');
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

    <form:form method="post" action="${pageContext.request.contextPath}/creature/admin/update/${creatureUpdate.id}"
               modelAttribute="creatureUpdate" cssClass="form-horizontal" enctype="multipart/form-data">
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
                <form:input path="height" cssClass="form-control" />
                <form:errors path="height" cssClass="help-block"/>
            </div>
        </div>
                    <div class="form-group ${name_error?'has-error':''}">
            <form:label path="weight" cssClass="col-sm-2 control-label">Weight</form:label>
            <div class="col-sm-10">
                <form:input path="weight" cssClass="form-control" />
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
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="multipartImage" cssClass="col-sm-2 control-label">Image</form:label>
            <div class="col-sm-10">
                <form:input path="multipartImage" cssClass="form-control" type="file"/>
                <form:errors path="multipartImage" cssClass="help-block"/>
            </div>
        </div>
      
        <button class="btn btn-primary" type="submit">Update creature</button>
        <span style="color: red; display: none" id="enter_name" class="input-err">- You must <strong>name</strong> the creature.</span>
        <span style="color: red; display: none" id="height_err" class="input-err">- The <strong>height</strong> of the creature must be a positive number.</span>
        <span style="color: red; display: none" id="weight_err" class="input-err">- The <strong>weight</strong> of the creature must be a positive number.</span>
        <span style="color: red; display: none" id="agi_err" class="input-err">- The <strong>agility</strong> of the creature must be an integer in the interval [0, 10].</span>
        <span style="color: red; display: none" id="fero_err" class="input-err">- The <strong>ferocity</strong> of the creature must be an integer in the interval [0, 10].</span>
    </form:form>

</jsp:attribute>
</my:template>