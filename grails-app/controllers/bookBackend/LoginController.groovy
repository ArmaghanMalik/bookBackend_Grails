package bookBackend
import org.springframework.beans.factory.annotation.Value

class LoginController {

    @Value('${admin.username}')
    String adminUsername

    @Value('${admin.password}')
    String adminPassword

    def index() {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
        response.setHeader("Pragma", "no-cache")
        response.setDateHeader("Expires", 0)
        render(view: "login")
    }

    def login() {
        if (params.username == adminUsername && params.password == adminPassword) {
            session.user = [username: adminUsername, isAdmin: true]
            redirect(controller: 'admin', action: 'index')
            return
        }

        User user = User.findByUsernameAndPassword(params.username, params.password)
        if (user && user.enabled) {
            session.user = user
            redirect(controller: 'user', action: 'dashboard')
        } else {
            flash.error = "Incorrect username or password"
            redirect(action: 'index')
        }
    }

    def logout() {
        session.user = null
        session.invalidate()
        //session=request.getSession(false)
        redirect(action: 'index')
    }
}
