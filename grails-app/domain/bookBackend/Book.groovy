package bookBackend

class Book {
    String title
    String author
    String isbn
    BigDecimal price

    static hasMany = [purchases: Purchase]

    static constraints = {
        title blank: false
        author blank: false
        isbn blank: false, unique: true
        price min: 0.0
    }

    static mapping = {
        purchases cascade: 'none'
    }

    static namedQueries = {
        existingBookByIsbn { String isbn->
            eq('isbn',isbn)
        }
    }
}