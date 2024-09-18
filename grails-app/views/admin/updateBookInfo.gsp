<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Update Book Information</title>

    <style>
        /* all labels have the same width to align the fields */
        .form-group label {
            display: inline-block;
            width: 120px; /* Adjust width */
        }
    </style>

    <g:javascript>
        // To auto-hide flash messages after a few seconds
        setTimeout(function() {
        let flashMessages = document.querySelector('.flash-message');
        if (flashMessages) {
        flashMessages.style.display = 'none';
        }
        }, 3000); // 3 seconds
    </g:javascript>

</head>
<body>
<h1>Update Book Information</h1>

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

<g:form action="updateBookInfo">
    <g:hiddenField name="id" value="${params.id}"/>

    <div class="form-group">
        <label for="title">Book Title:</label>
        <g:textField name="title" value="${book?.title}" type="text"/>
    </div>

    <div class="form-group">
        <label for="newBookAuthor">Author:</label>
        <g:textField name="author" value="${book?.author}"/>
    </div>

    <div class="form-group">
        <label for="newBookIsbn">ISBN:</label>
        <g:textField name="isbn" value="${book?.isbn}"/>
    </div>

    <div class="form-group">
        <label for="newBookPrice">Price:</label>
        <g:textField name="price" value="${book?.price}"/>
    </div>

    <div class="form-group">
        <g:submitButton name="update" value="Update Book" class="btn btn-primary"/>
    </div>
</g:form>

    <div class="form-group">
        <g:link action="index" class="btn btn-secondary">Back to Home</g:link>
    </div>

</body>
</html>
