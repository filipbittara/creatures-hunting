<%-- 
    Document   : login
    Created on : 16.12.2015, 21:21:19
    Author     : Ondrej Klein
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:template-no-login>
    <jsp:attribute name="body">
        <div class="container">

            <form id="loginform" class="form-signin" action="<c:url value='login/login-check' />" method="POST">
                <h2 class="form-signin-heading">Please sign in</h2>
                <label for="inputUsername" class="sr-only">Username</label>
                <input name="username" type="text" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
                <label for="inputPassword" class="sr-only">Password</label>
                <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                <button class="btn btn-lg btn-primary btn-block" name="submit" type="submit">Sign in</button>
            </form>

        </div> <!-- /container -->
        <script>
            $('#loginform input[type=text],input[type=password]').on('change invalid', function() {
                var textfield = $(this).get(0);
                textfield.setCustomValidity('');
                if (!textfield.validity.valid) {
                  textfield.setCustomValidity('Fill in the box, please.');  
                }
            });            
        </script>
    </jsp:attribute>
</my:template-no-login>
