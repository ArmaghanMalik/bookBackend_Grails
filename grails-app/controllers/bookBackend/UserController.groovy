package bookBackend
import grails.gorm.CriteriaBuilder
import grails.gorm.transactions.Transactional

@Transactional
class UserController {

    def index() {
        redirect(action: 'dashboard')
    }

    def dashboard() {
        if (!session.user) {
            redirect(controller: 'login', action: 'index')
            return
        }
        Book bestSeller=getBestSeller()
        Map purchasedBooksData = purchasedBooks(session.user.id)
        List<Book> purchasedBooks = purchasedBooksData.books
        List<Book> allBooks = Book.list()
        render(view: 'dashboard', model: [user: session.user, books: allBooks, purchasedBooks: purchasedBooks,bestSeller:bestSeller])
    }

    def purchaseBook(Long id) {
        if (!session.user) {
            redirect(controller: 'login', action: 'index')
            return
        }
        String email = session.user.email
        Book book = Book.get(id)
        User user = session.user

        if (book) {
            Purchase purchase = new Purchase(user: user, book: book, userEmail: email, purchaseDate: new Date())
            if (purchase.save(flush: true)) {
                flash.message = "Book Purchased Successfully!"
                redirect(action: "dashboard")
            } else {
                flash.error = "Failed to purchase Book"
                render(view: 'dashboard', model: [book: book])
            }
        } else {
            flash.error = "Book not found"
            render(view: 'dashboard', model: [book: book])
        }
    }

    def changePassword() {
        if (!session.user) {
            redirect(controller: 'login', action: 'index')
            return
        }
        if (request.method == 'POST') {
            String currentPassword = params.currentPassword
            String newPassword = params.newPassword
            User user = User.get(session.user.id)

            if (user && user.password == currentPassword) {
                user.password = newPassword
                user.save(flush: true)
                flash.meassge = "Password changed successfully"
                redirect(action: 'dashboard')
            } else {
                flash.error = "Incorrect Password"
                render(view: 'changePassword')
            }
        } else {
            render(view: 'changePassword')
        }
    }

    def purchasedBooks (long userId) {
        User user = User.get(userId)
        List<Book> userBooks = Purchase.findAllByUser(user).collect { it.book }
        [books: userBooks]
    }

    def getBestSeller() {
        List<Object> bestSeller = Purchase.createCriteria().get {
            projections {
                groupProperty('book')
                count('book', 'bookCount')
            }
            order('bookCount', 'desc')
            maxResults(1)
        }
        if (bestSeller) {
            Book book = bestSeller[0]
            return  book
        } else {
            return  null
        }
    }
}
