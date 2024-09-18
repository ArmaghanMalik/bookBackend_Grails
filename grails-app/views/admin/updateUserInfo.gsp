<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Update User Information</title>

    <style>
        /* all labels have the same width to align the fields */
        .form-group label {
            display: inline-block;
            width: 120px; /* Adjust width  */
        }
    </style>

    <g:javascript>
        setTimeout(function() {
            let flashMessages = document.querySelector('.flash-message');
            if (flashMessages) {
                flashMessages.style.display = 'none';
            }
        }, 3000);
    </g:javascript>

</head>
<body>
    <h1>Update User Information</h1>

    <!-- Flash Message Section -->
    <div class="flash-message">
        <g:if test="${flash.message}">
            <div class="alert alert-success">${flash.message}</div>
        </g:if>
        <g:if test="${flash.error}">
            <div class="alert alert-danger">${flash.error}</div>
        </g:if>
    </div>


    <g:hasErrors beans="${user}">
        <div class="alert alert-danger">
            <g:eachError bean="${user}" var="error">
                <p>${error.field}: ${error.message}</p>
            </g:eachError>
        </div>
    </g:hasErrors>

    <g:form action="updateUserInfo" method="post">
        <g:hiddenField name="id" value="${params.id}"/>

        <div class="form-group">
            <label>First Name:</label>
            <g:textField name="firstName" value="${user?.firstName}"/>
        </div>

        <div class="form-group">
           <label>Last Name:</label>
           <g:textField name="lastName" value="${user?.lastName}"/>
        </div>

        <div class="form-group">
            <label>Email:</label>
            <g:textField name="email" value="${user?.email}"/>
        </div>

        <div class="form-group">
            <label>Phone:</label>
            <g:textField name="phone" value="${user?.phone}"/>
        </div>

        <div class="form-group">
            <label>Username:</label>
            <g:textField name="username" value="${user?.username}"/>
        </div>

        <div class="form-group">
            <label>Password:</label>
            <g:passwordField name="password" value="${user?.password}"/>
        </div>

        <div class="form-group">
            <label>Enabled:</label>
            <g:checkBox name="enabled" value="${user?.enabled}"/>
        </div>

        <div class="form-group">
        <g:submitButton name="update" value="Update User" class="btn btn-primary"/>
        </div>
    </g:form>

    <div class="form-group">
    <g:link action="index" class="btn btn-secondary">Back to Home</g:link>
    </div>
</body>
</html>
