package bookBackend

class User {
    String username
    String password
    String firstName
    String lastName
    String email
    String phone
    boolean enabled
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false
    boolean isAdmin

    static hasMany = [purchases: Purchase]

    static constraints = {
        username blank: false, unique: true
        password blank: false, password: true
        firstName blank: false
        lastName blank: false
        email blank: false, unique: true
        phone blank: false
        enabled blank: false

    }

    static mapping = {
        password column: '`password`'
    }

    static namedQueries = {
        existingByUsername {String username->
            eq('username', username)
        }
         existingByEmail {String email ->
             eq('email', email)
         }
        existingByPhone {String phone ->
            eq('phone', phone)
        }
    }

    def beforeDelete() {
        Purchase.withTransaction {
            Purchase.findAllByUser(this).each { purchase ->
                purchase.user = null
                purchase.save(flush: true)
            }
        }
    }
}