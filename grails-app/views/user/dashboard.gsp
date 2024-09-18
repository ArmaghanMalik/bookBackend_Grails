<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Dashboard</title>

    <g:javascript>
        setTimeout(function() {
        let flashMessages = document.querySelector('.flash-message');
        if (flashMessages) {
        flashMessages.style.display = 'none';
        }
        }, 5000); // 5 seconds
    </g:javascript>

    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        .flash-message {
            margin: 16px;
        }

        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 16px;
        }

        .content {
            width: 100%;
            max-width: 1200px;
            margin-top: 16px;
            padding-right: 20px;
        }

        .best-seller-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 16px;
            margin-bottom: 16px;
            width: 100%;
            max-width: 550px; /* Adjust based on your preference */
            box-shadow: 2px 2px 12px rgba(0, 0, 0, 0.1);
            background-color: #f9f9f9;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .best-seller-text {
            display: flex;
            align-items: center;
            font-size: 18px;
            font-family: 'Verdana', sans-serif;
            color: #333;
        }

        .best-seller-text span {
            margin-right: 10px;
            font-weight: bold;
        }

        .btn {
            display: inline-block;
            padding: 8px 16px;
            font-size: 14px;
            font-weight: bold;
            text-align: center;
            text-decoration: none;
            border: 1px solid transparent;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s, border-color 0.3s;
        }

        .btn-primary {
            background-color: #007bff;
            color: #fff;
            border-color: #007bff;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #004085;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 16px 0;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .form-group {
            margin-top: 16px;
        }
    </style>
</head>
<body>
<h1>Welcome, ${session.user.firstName}!</h1>
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

<div class="container">
    <g:if test="${bestSeller}">
        <!-- Best Seller Section (Horizontal Card) -->
        <div class="best-seller-card">
            <!-- Best Seller Header -->
            <div class="best-seller-header">
                <h1>Best Seller</h1>
            </div>
            <!-- Best Seller Details -->
            <div class="best-seller-details">
                <span>Title: ${bestSeller.title}</span>
            </div>
            <div class="best-seller-details">
                <span>Author: ${bestSeller.author}</span>
            </div>
            <div class="best-seller-details">
                <span>Price: ${bestSeller.price}</span>
            </div>
            <div class="best-seller-buy">
                <g:link action="purchaseBook" id="${bestSeller.id}" class="btn btn-primary">Buy</g:link>
            </div>
        </div>
    </g:if>

    <div class="content">
        <h2>Available Books</h2>
        <table>
            <thead>
            <tr>
                <th>Book Title</th>
                <th>Book Author</th>
                <th>Book Price</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${books}" var="book">
                <tr>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.price}</td>
                    <td>
                        <g:link action="purchaseBook" id="${book.id}" class="btn btn-primary">Buy</g:link>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>

        <h2>Books Purchased by ${user?.firstName}</h2>
        <table>
            <thead>
            <tr>
                <th>Book Title</th>
                <th>Book Author</th>
                <th>Book Price</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${purchasedBooks}" var="book">
                <tr>
                    <td>${book?.title}</td>
                    <td>${book?.author}</td>
                    <td>${book?.price}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>

<div class="form-group">
    <g:form controller="user" action="changePassword">
        <g:submitButton name="Change Password" value="Change Password"/>
    </g:form>
</div>

<g:form controller="login" action="logout">
    <g:submitButton name="Logout" value="Logout"/>
</g:form>

</body>
</html>
