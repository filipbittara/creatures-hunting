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

        <div class="container">


                    <b>To anyone reading this, I probably won't be alive to tell you:</b><br />
                    Are you really alive?<br />
                    Sorry, I ment to say welcome. Of course you are. I just... thought that everyone is dead.<br />
                    Since these creatures emerged everywhere, there are not many people wandering around.<br />
                    I don't really know what happend. So if you're trying to find out how this hell started, you won't find it here.<br />
                    But maybe things you'll find here, can keep you alive a little longer.<br />
                    <br />
                    You know, I used to be a programmer. A good one, too. I wanted to be best, work for Google, earn millions, marry beautiful girl...<br />
                    Well, it doesn't matter anymore. Nothing matters anymore.<br />
                    So, to the point: I've created this system to keep track of all the creatures roaming outside.<br />
                    How they look, what's their weakness, where they are, what weapon to use... I've even named every one of them.<br />
                    You can look at them <my:a href="/creature/list">here</my:a>, but not right now. I need to tell you more.<br />
                        It also contains all interesting areas, but not gonna lie to you, most of them are here just because one of them shits was there.<br />
                        Later, you can browse areas <my:a href="/area/list">here</my:a>.<br />
                        And finally, I record everything, that can be used as weapon.<br />
                        Most of these things you'll find scattered across my bunker. But you probably already noticed that.<br />
                    <my:a href="/weapon/list">Here</my:a> you can see all usefull informations about weapons.<br />
                        <br />
                        You can access everything from menu on the top of page.<br />
                        Clicking on item will expand it, and you'll see more informations.<br />
                        You can filter every table, based on keyword. You just have to write it to the box above table.<br /> 
                        In corresponding page, you'll be able to link creatures to certain location, if you've seen it there or unlink them if not.<br />
                        Same thing goes with weapons.<br />  
                        <br />
                        And I've developed some tools, that can help you even more.<br />
                        <a data-toggle="collapse" data-target="#creaturesInCircle" class="accordion-toggle clickable">This</a> tells you what creatures are around you, if you'll suply it with coordinates and radius.<br />

                        <div class="accordian-body collapse" id="creaturesInCircle">

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
                            <input type="button" value="Show" class="btn btn-primary" onclick="location.href = '${pageContext.request.contextPath}' + '/creature/circle/' + x.value + '/' + y.value + '/' + radius.value"/>
                        </div>

                    </div>



                    And
                    <a data-toggle="collapse" data-target="#neededWeapons" class="accordion-toggle clickable">this</a>   will tell you, what weapons you should bring with yourself, if you insert areas you plan to cross. <br />
                    <div class="accordian-body collapse" id="neededWeapons">
                        <div class="container">
                            <div class="row"> 
                                <div class="control-group" id="fields"> 
                                    <div class="controls"> 
                                        <form id="areasw" role="form" action="${pageContext.request.contextPath}/weapon/areas" autocomplete="off" method="post"> 
                                            <label cssClass="col-sm-2 control-label">Fill in areas</label>
                                            <div class="entry input-group col-xs-3">
                                                <select class="form-control" name="field">
                                                    <option></option>
                                                    <c:forEach var="area" items="${areas}">
                                                        <option>${area.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <!--<input class="form-control" name="field" type="text" placeholder="Type something" />--> 
                                                <span class="input-group-btn"> 
                                                    <button class="btn btn-success btn-add" type="button"> 
                                                        <span class="glyphicon glyphicon-plus"></span> 
                                                    </button> 
                                                </span> 
                                            </div>

                                        </form>

                                        <button type="submit" value=" Send" class="btn btn-success" id="submit" onclick="areaWeaponFormSubmit()" />Submit</button> 

                                        <br> 
                                        <small>Press <span class="glyphicon glyphicon-plus gs"></span> to add another form field</small> 
                                    </div> 
                                </div> 
                            </div> 

                        </div>
                    </div>
                    <br />
                    I must end now and grab my gun, I hear them on the stairs.<br />
                    I will try to add more functions later.<br />
                    <br />
                    <b>In the event of my death:</b><br />
                    Admin password is in the second drawer on the right side. The key is under doormat.<br />
                    Please, keep this system running and updated, not just for yourself but for any future users. If anyone else survived.<br />
                    It's not that hard.<br />




                </div>







                <script>
                    function areaWeaponFormSubmit() {
                        document.getElementById("areasw").submit();
                    }

                    $(function () {
                        $(document).on('click', '.btn-add', function (e) {
                            e.preventDefault();
                            var controlForm = $('.controls form:first'), currentEntry = $(this).parents('.entry:first'), newEntry = $(currentEntry.clone()).appendTo(controlForm);
                            newEntry.find('input').val('');
                            controlForm.find('.entry:not(:last) .btn-add').removeClass('btn-add').addClass('btn-remove').removeClass('btn-success').addClass('btn-danger').html('<span class="glyphicon glyphicon-minus"></span>');
                        }).on('click', '.btn-remove', function (e) {
                            $(this).parents('.entry:first').remove();
                            e.preventDefault();
                            return false;
                        });
                    });
                </script>

            </jsp:attribute>
        </my:template>
