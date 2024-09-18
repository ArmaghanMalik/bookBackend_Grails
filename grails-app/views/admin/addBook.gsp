<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Add Book</title>

    <style>
        /* all labels have the same width to align the fields */
        .form-group label {
            display: inline-block;
            width: 120px; /* Adjust width */
        }
    </style>

    <g:javascript>
        setTimeout(function(){
            let flashMessages = document.querySelector('.flash-message');
            if (flashMessages) {
                flashMessages.style.display = 'none';
            }
        }, 3000;
    </g:javascript>
</head>
<body>
    <h1>Add Book</h1>

    <div class="flash-message">
        <g:if test = "${flash.message}">
            <div class="alert alert-success">${flash.message}</div>
        </g:if>
        <g:if test = "${flash.error}">
            <div class = "alert alert-danger">${flash.error}</div>
        </g:if>
    </div>

    <g:hasErrors bean = "${user}">
        <div class="alert alert-danger">
            <g:eachError bean="${user}" var="error">
                <p>${error.field}: ${error.message}</p>
            </g:eachError>
        </div>
    </g:hasErrors>

    <g:form action="addBook">

        <div class="form-group">
        <label>Title:</label>
        <g:textField name="title" value="${book?.title}"/>
        </div>

        <div class="form-group">
        <label>Author:</label>
        <g:textField name="author" value="${book?.author}"/>
        </div>

        <div class="form-group">
        <label>ISBN:</label>
        <g:textField name="isbn" value="${book?.isbn}"/>
        </div>

        <div class="form-group">
        <label>Price:</label>
        <g:textField name="price" value="${book?.price}"/>
        </div>

        <g:submitButton name="Add Book"/>
    </g:form>
</body>
</html>