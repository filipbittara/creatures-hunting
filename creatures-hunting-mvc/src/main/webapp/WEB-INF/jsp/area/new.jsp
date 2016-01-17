<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="New area">
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

                $('input#latitude').on("change paste keyup", function() {
                    if ($('input#latitude').val().length == 0
                            || parseFloat($('input#latitude').val()) < -180
                            || parseFloat($('input#latitude').val()) > 180
                            || $.isNumeric($('input#latitude').val()) == false) {
                        $('#lat_err').css('display', 'block');
                    } else {
                        $('#lat_err').css('display', 'none');
                    }
                    console.log($('span.input-err:visible').length);
                    if ($('span.input-err:visible').length == 0) {
                        $('button[type="submit"]').removeAttr('disabled');
                    } else {
                        $('button[type="submit"]').attr('disabled', 'disabled');
                    }
                });

                $('input#longitude').on("change paste keyup", function() {
                    if ($('input#longitude').val().length == 0
                            || parseFloat($('input#longitude').val()) < -180
                            || parseFloat($('input#longitude').val()) > 180
                            || $.isNumeric($('input#longitude').val()) == false) {
                        $('#lon_err').css('display', 'block');
                    } else {
                        $('#lon_err').css('display', 'none');
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
      
        <button class="btn btn-primary" disabled="disabled" type="submit">Create area</button>
        <span style="color: red; display: block" id="enter_name" class="input-err">You must <strong>name</strong> the area.</span>
        <span style="color: red; display: block" id="lat_err" class="input-err"><strong>Latitude</strong> must be in the interval [-180; 180].</span>
        <span style="color: red; display: block" id="lon_err" class="input-err"><strong>Longitude</strong> must be in the interval [-180; 180].</span>
    </form:form>

</jsp:attribute>
</my:template>