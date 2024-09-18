<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Login</title>

    <style>
        /* Ensure all labels have the same width to align the fields */
        .form-group label {
            display: inline-block;
            width: 120px; /* Adjust width as necessary */
        }
    </style>

    <g:javascript>
        // Optional: To auto-hide flash messages after a few seconds
        setTimeout(function() {
            let flashMessages = document.querySelector('.flash-message');
            if (flashMessages) {
                flashMessages.style.display = 'none';
            }
        }, 5000); // 5 seconds
    </g:javascript>

</head>
<body>
    <h1>Login</h1>

           <!-- Flash Message Section -->
            <div class="flash-message">
                <g:if test="${flash.message}">
                    <div class="alert alert-success">${flash.message}</div>
                </g:if>
                <g:if test="${flash.error}">
                    <div class="alert alert-danger">${flash.error}</div>
                </g:if>
            </div>

            <!-- Validation Errors -->
            <g:hasErrors bean="${user}">
                <div class="alert alert-danger">
                    <g:eachError bean="${user}" var="error">
                        <p>${error.field}: ${error.message}</p>
                    </g:eachError>
                </div>
            </g:hasErrors>

    <!-- Ensure the form submits using the POST method -->
    <g:form action="login" method="POST">

        <div class="form-group">
        <label>Username:</label>
        <g:textField name="username"/><br/>
        </div>

        <div class="form-group">
        <label>Password:</label>
        <g:passwordField name="password"/><br/>
        </div>

        <g:submitButton name="login" value="Login"/>
    </g:form>

</body>
</html>
