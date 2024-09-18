package bookBackend

import grails.gorm.transactions.Transactional
import org.apache.cxf.jaxws.handler.types.CString

@Transactional
class AdminController {

    def index() {
        List<User> users = User.list(sort: 'id', order: 'desc')
        List<Book> books = Book.list(sort: 'id', order: 'desc')
        List<Purchase> purchases = Purchase.list(sort: 'id', order: 'desc')
        render(view: "/admin/index", model: [users: users, books: books, purchases: purchases])
    }
    def EmailService

    def addUser() {
        String username = params.username
        String email = params.email
        String phone = params.phone
        User existingUsername = User.existingByUsername(username).get()
        User existingEmail = User.existingByEmail(email).get()
        User existingPhone = User.existingByPhone(phone).get()

        //add avoce checks in names query if true then save
        try {
            if (request.method == "POST") {
                String firstName = params.firstName
                String lastName = params.lastName
                String password = params.password
                boolean enabled = params.enabled
                def emailPattern = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/
                if (!firstName || !lastName || !email || !password || !phone) {
                    flash.message = "Fill the required fields"
                } else if (firstName.isNumber()) {
                    flash.error = "Invalid format of field First Name!"
                } else if (lastName.isNumber()) {
                    flash.error = "Invalid format of field Last Name!"
                } else if (!phone.isNumber()) {
                    flash.error = "Phone number must contain digits only!"
                } else if (!(email ==~ emailPattern)) {
                    flash.error = "Invalid format of email: '${email}'!"
                } else if (username.isNumber()) {
                    flash.error = "Invalid format of username!"
                } else if (existingEmail) {
                    flash.error = "User with email: '${email}' already exists!"
                } else if (existingUsername) {
                    flash.error = "User with username: '${username}' already exists!"
                } else if (existingPhone) {
                    flash.error = "User with phone number: '${phone}' already exists!"
                } else {
                    User user = new User(firstName: firstName, lastName: lastName, email: email, phone: phone, username: username, password: password, enabled: enabled)
                    if (user.save(flush: true)) {
                        EmailService.sendWelcomeEmail(user)
                        flash.message = "User saved successfully!"
                        redirect(action: "index")
                        return
                    } else {
                        flash.error = "Failed to save user!"
                    }
                }
                render(view: "addUser", model: [user: new User(params)])
            }
        } catch (Exception e) {
            log.error "Error in adding User!", e
            render(view: "addUser", model: [user: new User(params)])
        }
    }

    def addBook() {
        String isbn = params.isbn
        Book existingBook = Book.existingBookByIsbn(isbn).get()
        try {
            if (request.method == 'POST') {
                String author = params.author
                Book book = new Book(params)

                if (existingBook) {
                    flash.error = "Book already exists"
                } else if (!params.title){
                    flash.error = "Title field is missing!"
                } else if (!author){
                    flash.message = "Add Book details!"
                } else if (!params.isbn){
                    flash.error = "Isbn is missing!"
                } else if (!params.price){
                    flash.error = "Price field is missing!"
                } else if (author.isNumber()){
                    flash.error = "Invalid format of field author!"
                } else if (!params.isbn.isNumber()){
                    flash.error = "Invalid format!, Isbn must be digits."
                } else {
                    if (book.save(flush: true)) {
                        flash.message = "Book saved successfully!"
                        redirect(action: "index")
                        return
                    } else {
                        flash.error = "failed to add book!"
                    }
                }
                render (view: "addBook", model: [book: new Book(params)])
            } else {
                render(view: "addBook", model: [book: new Book(params)])
            }
        } catch (Exception e) {
            log.error "Error in addBook action", e
            render view: '/error', model: [exception: e]
        }
    }

    def removeBook() {
        Book book = Book.get(params.id)
        if (book) {
            try {
                book.purchases*.delete() // Delete associated purchases
                book.delete(flush: true, failOnError: true)
                flash.message = "Book deleted successfully"
            } catch (Exception e) {
                flash.error = "Error deleting book: ${e.message}"
            }
        } else {
            flash.error = "Book not found"
        }
        redirect(controller: 'admin', action: 'index')
    }

    def removeUser() {
        User user = User.get(params.id)
        if (user) {
            user.beforeDelete()
            user.delete(flush: true, failOnError: true)
            flash.message = "User removed successfully"
        }
        redirect(controller: 'admin', action: 'index')
    }

    def updateBookInfo() {
        Book book = Book.get(params.id)
        if (!book) {
            flash.error = "Book not found"
            redirect(action: 'index')
            return
        }
        if (request.method == "POST") {
            // Input parameters
            String title = params.title
            String author = params.author
            String isbn = params.isbn
            String price = params.price

            // Regex patterns
            def isbnPattern = /^\d+$/
            def pricePattern = /^\d+$/

            // Validate fields
            if (!title || !author || !isbn || !price) {
                flash.error = "All fields are required"
            } else if (!isbn.matches(isbnPattern)) {
                flash.error = "Invalid ISBN format"
            } else if (author.isNumber()) {
                flash.error = "Author name cannot contain numbers"
            } else if (!price.matches(pricePattern)) {
                flash.error = "Invalid price format"
            } else {
                // All checks passed, update book details
                book.title = title
                book.author = author
                book.isbn = isbn
                book.price = price as BigDecimal

                if (book.save(flush: true)) {
                    flash.message = "Book information updated"
                    redirect(action: 'index')
                    return
                } else {
                    flash.error = "Failed to update book information"
                }
            }
            // Render the update form again in case of any validation error
            render(view: "updateBookInfo", model: [book: book])
        } else {
            render(view: "updateBookInfo", model: [book: book])
        }
    }

    def updateUserInfo() {
        User user = User.get(params.id)
        if (!user) {
            flash.error = "User not found"
            redirect(action: "index")
            return
        }
        if (request.method == "POST") {
            // Input parameters
            String firstName = params.firstName
            String lastName = params.lastName
            String email = params.email
            String username = params.username
            String password = params.password
            String phone = params.phone
            def enabled = params.enabled

            def emailPattern = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/

            // Check for existing users with the same email, username, or phone but not the current user
            User existingUser = User.find {
                (email == params.email && id != user.id) ||
                (username == params.username && id != user.id) ||
                (phone == params.phone && id != user.id)
            }
            // Validate form fields
            if (!firstName || !lastName || !email || !phone || !username || !password) {
                flash.error = "All fields are required"
            } else if (!(email ==~ emailPattern)) {
                flash.error = "Invalid email format"
            } else if (username.isNumber()) {
                flash.error = "Invalid username format"
            } else if (firstName.isNumber()) {
                flash.error = "First name cannot contain numbers"
            } else if (lastName.isNumber()) {
                flash.error = "Last name cannot contain numbers"
            } else if (!phone.isNumber()) {
                flash.error = "Phone number must only contain digits"
            } else if (existingUser) {
                // Error messages if duplicate email, username, or phone exist
                List<String> errorMessages = []
                if (email == existingUser.email) {
                    errorMessages.add("User already exists with email: '${email}'!")
                }
                if (username == existingUser.username) {
                    errorMessages.add("User already exists with username: '${username}'!")
                }
                if (phone == existingUser.phone) {
                    errorMessages.add("User already exists with phone number: '${phone}'!")
                }
                flash.error = errorMessages.join(", ")
            } else {
                // Update user details if no errors
                user.firstName = firstName
                user.lastName = lastName
                user.email = email
                user.phone = phone
                user.username = username
                user.password = password
                user.enabled = enabled == "on"

                if (user.save(flush: true)) {
                    flash.message = "User information updated successfully!"
                    redirect(action: "index")
                    return
                } else {
                    flash.error = "Failed to update user information"
                }
            }
            // If validation issues
            render(view: "updateUserInfo", model: [user: user])
        } else {
            render(view: "updateUserInfo", model: [user: user])
        }
    }
}