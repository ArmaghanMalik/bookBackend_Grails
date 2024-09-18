<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admin Panel</title>

        <g:javascript>
            // To auto-hide flash messages after a few seconds
            setTimeout(function() {
                let flashMessages = document.querySelector('.flash-message');
                if (flashMessages) {
                    flashMessages.style.display = 'none';
                }
            }, 5000); // 5 seconds
        </g:javascript>

</head>
<body>
    <h1>Admin Panel</h1>

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

    <h2>Users</h2>
    <table>
        <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Enabled</th>
                <th>Action</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${users}" var="user">
                <tr>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.phone}</td>
                    <td>${user.enabled}</td>
                    <td>
                        <g:link controller="admin" action="removeUser" id="${user.id}">Remove</g:link>
                    </td>
                    <td>
                        <g:link controller="admin" action="updateUserInfo" id="${user.id}">Update</g:link>
                    </td>
                </tr>
            </g:each>
        </tbody>
    </table>

    <g:form controller="admin" action="addUser" method="POST">
        <g:submitButton name="Add User" value="Add User"/>
    </g:form>

    <h2>Books</h2>
    <table>
        <thead>
            <tr>
                <th>Title</th>
                <th>Author</th>
                <th>ISBN</th>
                <th>Price</th>
                <th>Action</th>
                <th>Action</th>
                <th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${books}" var="book">
                <tr>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.isbn}</td>
                    <td>${book.price}</td>
                     <td>
                         <g:link controller="admin" action="removeBook" id="${book.id}">Remove</g:link>
                     </td>
                     <td>
                        <g:link controller="admin" action="updateBookInfo" id="${book.id}">Update</g:link>
                     </td>
                </tr>
            </g:each>
        </tbody>
    </table>

     <g:form controller="admin" action="addBook" method="post">
        <g:submitButton name="Add Book" value="Add Book"/>
     </g:form>

    <h3>All Purchases</h3>
    <table>
        <thead>
            <tr>
                <th>User</th>
                <th>Email</th>
                <th>Book</th>
                <th>Purchased Date</th>
            </tr>
        </thead>
        <tbody>
            <g:each in="${purchases}" var="purchase">
                <tr>
                    <td>${purchase.user?.username}</td>
                    <td>${purchase?.userEmail}</td>
                    <td>${purchase.book?.title}</td>
                    <td>${purchase?.purchaseDate}</td>
                </tr>
            </g:each>
        </tbody>
        </table>

    <g:form controller="login" action="logout">
         <g:submitButton name="Logout" value="Logout"/>
    </g:form>

</body>
</html>