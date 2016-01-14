<%-- 
    Document   : home
    Created on : 10.12.2015, 18:51:01
    Author     : Ondrej Klein
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template title="Home">
<jsp:attribute name="body">
    <script  type="text/javascript">
        function submit_circle() {
            if(event.keyCode == 13) {
                location.href = '${pageContext.request.contextPath}' + '/home/circle/' + filter_page.value + '/' + '/'        
            }
        }
    </script>
    <div class="input-group col-md-6">
        <div>
            <label cssClass="col-sm-2 control-label">Longitude</label>
            <input type="text" id="x" class="form-control" value="${x}"/>
        </div>
        <div>
            <label cssClass="col-sm-2 control-label">Latitude</label>
            <input type="text" id="y" class="form-control" value="${y}"/>
        </div>
        <div>
            <label cssClass="col-sm-2 control-label">Radius</label>
            <input type="text" id="radius" class="form-control" value="${radius}"/>
        </div>
        <input type="button" value="Show" class="btn btn-primary" onclick="location.href = '${pageContext.request.contextPath}' + '/home/circle/' + x.value + '/'+ y.value + '/' + radius.value"/>
    </div>
    <c:if test="${circle != '' && circle != null}">
        <br />
        <b>Creatures in given circle: ${circle}.</b>
    </c:if>
</jsp:attribute>
</my:template>
