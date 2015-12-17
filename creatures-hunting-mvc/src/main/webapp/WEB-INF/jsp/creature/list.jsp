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

<my:template title="List of creatures">
<jsp:attribute name="body">
    <my:a href="/creature/new" class="btn btn-primary">
        Add new creature
    </my:a>
        
    <table class="table">
        <thead>
        <tr>
            <!--<th>Id</th>-->
            <th>Name</th>
            <th>Type</th>
            <th>Weakness</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${creatures}" var="creature">
            <tr>
                <!--<td>${creature.id}</td>-->
                <td data-toggle="collapse" data-target="#${creature.id}detail" class="accordion-toggle clickable"</td>
                ${creature.name}</td>
                <td data-toggle="collapse" data-target="#${creature.id}detail" class="accordion-toggle clickable"</td>
                ${creature.type}</td>
                <td data-toggle="collapse" data-target="#${creature.id}detail" class="accordion-toggle clickable"</td>
                ${creature.weakness}</td>
                <td>    
                    <form style="display: inline;" method="post" action="${pageContext.request.contextPath}/creature/delete/${creature.id}">                      
                        <button type="submit" class="btn btn-primary">Delete</button>  
                    </form>
                    <form style="display: inline;" method="get" action="${pageContext.request.contextPath}/creature/update/${creature.id}">                      
                        <button type="submit" class="btn btn-primary">Update</button>  
                    </form>
                </td>
                <!--<td><button class="btn btn-success">Details</span></button></td>-->
            </tr>
            <tr class="zeroPadding">
                <td colspan="4" class="hiddenRow" style="padding: 0; border-top-width: 0">
                    <div class="accordian-body collapse" id="${creature.id}detail">
                        <div class="row">
                            <div class="col-md-6">
                                <img class="image-responsive" src="${pageContext.request.contextPath}/creature/creatureImage/${creature.id}"/>
                            </div>
                            <div class="col-md-6">
                                <table class="table">
                                    <caption>Creature properties</caption>
                                    <!--<tr>
                                        <th>Id</th>
                                        <td>${creature.id}</td>
                                    </tr>-->
                                    <tr>
                                        <th>Name</th>
                                        <td>${creature.name}</td>
                                    </tr>
                                    <tr>
                                        <th>Type</th>
                                        <td>${creature.type}</td>
                                    </tr>
                                    <tr>
                                        <th>Height</th>
                                        <td>${creature.height} cm</td>
                                    </tr>
                                    <tr>
                                        <th>Weight</th>
                                        <td>${creature.weight} kg</td>
                                    </tr>
                                    <tr>
                                        <th>Agility</th>
                                        <td>${creature.agility}<b>/10</b></td>
                                    </tr>
                                    <tr>
                                        <th>Ferocity</th>
                                        <td>${creature.ferocity}<b>/10</b></td>
                                    </tr>
                                    <tr>
                                        <th>Weakness</th>
                                        <td>${creature.weakness}</td>
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