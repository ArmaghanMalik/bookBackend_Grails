package bookBackend

import com.examples._new.showbookservice.Book
import com.examples._new.showbookservice.BookList
import com.examples._new.showbookservice.BookServicePortType
import com.examples._new.showbookservice.DeleteBookRequestType
import com.examples._new.showbookservice.DeleteBookResponseType
import com.examples._new.showbookservice.GetAllBooksRequestType
import com.examples._new.showbookservice.GetAllBooksResponseType
import com.examples._new.showbookservice.GetBookRequestType
import com.examples._new.showbookservice.GetBookResponseType
import com.examples._new.showbookservice.AddBookRequestType
import com.examples._new.showbookservice.AddBookResponseType
import com.examples._new.showbookservice.UpdateBookRequestType
import com.examples._new.showbookservice.UpdateBookResponseType

import javax.jws.WebParam
import grails.gorm.transactions.Transactional
import org.grails.cxf.utils.GrailsCxfEndpoint

@GrailsCxfEndpoint
@Transactional
class BookDetailsSoapService implements BookServicePortType {
    @Override
    UpdateBookResponseType updateBook(@WebParam(partName = "parameters", name = "updateBookRequest", targetNamespace = "http://www.examples.com/new/showBookService") UpdateBookRequestType parameters) {

        UpdateBookResponseType response = new UpdateBookResponseType()
        String strIsbn = parameters.isbn?.toString()
        String title = parameters.title
        String author = parameters.author
        String strPrice = parameters.price?.toString()
        int id = parameters.id
        if (!id) {
            response.responseCode = 400
            response.responseDescription = "Invalid Id!"
            return response
        }
        bookBackend.Book backEndBook = new bookBackend.Book()
        backEndBook = bookBackend.Book.findById(id)
        if (!backEndBook) {
            response.responseCode = 400
            response.responseDescription = "Book not found!"
            return response
        }else if (!title || !author || !strIsbn || !strPrice) {
            response.responseCode = 400
            response.responseDescription = "Fill all the required fields"
            return response
        } else if (!strIsbn.isNumber()) {
            response.responseCode = 400
            response.responseDescription = "ISBN must be a number!"
            return response
        } else if (bookBackend.Book.findByIsbn(strIsbn?.toLong())) {
            response.responseCode = 400
            response.responseDescription = "ISBN must be unique!"
            return response
        } else if (!strPrice.isNumber()){
            response.responseCode = 400
            response.responseDescription = "price must be in digits!"
            return response
        }
        Long isbn = strIsbn.toLong()
        Long price = strPrice.toLong()
        backEndBook.title = title
        backEndBook.author = author
        backEndBook.isbn = isbn
        backEndBook.price = price
        if (backEndBook.save(flush: true)) {
            response.responseCode = 200
            response.responseDescription = "Successfully updated book information!"
            return response
        } else {
            response.responseCode = 400
            response.responseDescription = "Failed to update! Errors: ${backEndBook.errors.allErrors.join(", ")}"
        }
        return response
    }

    @Override
    AddBookResponseType addBook(@WebParam(partName = "parameters", name = "addBookRequest", targetNamespace = "http://www.examples.com/new/showBookService") AddBookRequestType parameters) {

        AddBookResponseType response = new AddBookResponseType()

        bookBackend.Book backEndBook = new bookBackend.Book()
        String title = parameters.title
        String author = parameters.author
        String strIsbn = parameters.isbn?.toString()
        String strPrice = parameters.price?.toString()
        if (!title || !author || !strIsbn || !strPrice) {
            response.responseCode = 400
            response.responseDescription = "Fill all the required fields"
            return response
        } else if (!strIsbn.isNumber()) {
            response.responseCode = 400
            response.responseDescription = "ISBN must be a number!"
            return response
        } else if (bookBackend.Book.findByIsbn(strIsbn?.toLong())) {
            response.responseCode = 400
            response.responseDescription = "ISBN must be unique!"
            return response
        } else if (!strPrice.isNumber()){
            response.responseCode = 400
            response.responseDescription = "price must be in digits!"
            return response
        } else {
            Long isbn = strIsbn.toLong()
            Long price = strPrice.toLong()
            backEndBook.title = title
            backEndBook.author = author
            backEndBook.isbn = (int) isbn
            backEndBook.price = price
            if (backEndBook.save(flush: true)) {
                response.responseCode = 200
                response.responseDescription = "Book added successfully!"
                return response
            } else {
                response.responseCode = 400
                response.responseDescription = "Unable to add book!"
            }
        }
        return response
    }

    @Override
    GetBookResponseType getBook(@WebParam(partName = "parameters", name = "getBookRequest", targetNamespace = "http://www.examples.com/new/showBookService") GetBookRequestType parameters) {

        GetBookResponseType response = new GetBookResponseType()

        int id = parameters.id
        if (!id) {
            response.responseCode = 400
            response.responseDescription = "Invalid Id!"
            return response
        }
        bookBackend.Book book = bookBackend.Book.findById(id)
//        book = bookBackend.Book.findById(id)
        if (!book) {
            response.responseCode = 400
            response.responseDescription = "Book not Found!"
            return response
        }
        Book rbook = new Book()
//        BookList bookList = new BookList()
        rbook.id = book.id
        rbook.title = book.title
        rbook.author = book.author
        rbook.isbn = book.isbn as int
        (rbook.price = (long) book.price)
//        bookList.book.add(rbook)

        response.book = rbook
        response.responseCode = 200
        response.responseDescription = "Book retrieved successfully"
        return response
    }

    @Override
    DeleteBookResponseType deleteBook(@WebParam(partName = "parameters", name = "deleteBookRequest", targetNamespace = "http://www.examples.com/new/showBookService") DeleteBookRequestType parameters) {
        DeleteBookResponseType response = new DeleteBookResponseType()

        int id = parameters.id
        if (!id) {
            response.responseCode = 400
            response.responseDescription = "Invalid Id"
            return response
        }
        bookBackend.Book book = new bookBackend.Book()
        book = bookBackend.Book.findById(id)
        if (!book) {
            response.responseCode = 400
            response.responseDescription = "Book not found!"
            return response
        }
        if (book) {
            book.delete(flush: true)
            response.responseCode = 200
            response.responseDescription = "Book deleted successfully!"
            return response
        } else {
            response.responseCode = 400
            response.responseDescription = "Failed to remove book!"
            return response
        }
    }

    @Override
    GetAllBooksResponseType getAllBooks(@WebParam(partName = "parameters", name = "getAllBooksRequest", targetNamespace = "http://www.examples.com/new/showBookService") GetAllBooksRequestType parameters) {
        GetAllBooksResponseType response = new GetAllBooksResponseType()

        def allBooks = bookBackend.Book.list()
        BookList bookList = new BookList()
        allBooks.each { backEndBook ->
            Book book = new Book()
            book.id = backEndBook.id
            book.title = backEndBook.title
            book.author = backEndBook.author
            book.isbn = backEndBook.isbn as int
            book.price = (long) backEndBook.price
            bookList.book.add(book)
        }
        response.book = bookList
        return response
    }
}
