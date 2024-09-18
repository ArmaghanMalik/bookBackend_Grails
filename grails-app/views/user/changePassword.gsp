<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Change Password</title>

    <style>
        /* all labels have the same width to align the fields */
        .form-group label {
            display: inline-block;
            width: 120px; /* Adjust width */
        }
    </style>

    <g:javascript>
        setTimeout(function() {
            let flashMessages = document.querySelector('.flash-message');
            if (flashMessages) {
                flashMessages.style.display = 'none';
            }
        }, 5000); // 5 seconds
    </g:javascript>

</head>
<body>
    <h1>Change Password</h1>

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

    <g:form action="changePassword">

        <div class="form-group">
        <label>Current Password:</label>
        <g:passwordField name="currentPassword"/><br/>
        </div>

        <div class="form-group">
        <label>New Password:</label>
        <g:passwordField name="newPassword"/><br/>
         </div>

        <g:submitButton name="change" value="Change Password"/>
    </g:form>

</body>
</html>
