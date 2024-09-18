package bookBackend

class AuthInterceptor {

    AuthInterceptor(){
        match(controller: 'user')
        match(controller: 'admin')
    }

    boolean before(){
        //If user not logged in
        if(!session.user) {
            redirect(controller: 'login', action: 'index')
            return false
        }
        //checks if the user is trying to access adminController's functions
        if(controllerName == 'admin' && !session.user.isAdmin ){
            redirect(controller: 'user', action: 'dashboard')
            return false
        }
        //checks if the admin is trying to access userController's functions
        if(controllerName == 'user' && session.user.isAdmin){
            redirect(controller: 'admin', action: 'index')
            return false
        }
        return true
    }
    boolean after(){
        return true
    }
}
