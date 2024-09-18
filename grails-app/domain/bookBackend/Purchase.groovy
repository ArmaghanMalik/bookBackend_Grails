package bookBackend

class Purchase {
    User user
    Book book
    Date purchaseDate
    String userEmail

    static constraints = {
        user nullable: true
        book nullable: false
        purchaseDate nullable: false
    }
    static mappings = {
        user cascade: 'none'
    }
}

