package bookBackend

class EmailService {

    def mailService

    def sendWelcomeEmail(User user) {
        mailService.sendMail {
                to user.email
                subject "Welcome Email"
                body "Dear ${user.firstName},\n\nWelcome to our platform!\nusername:${user.username}\npasssword=${user.password}"
        }
    }

    def sendDailyEmailToDisabledUsers() {
        def disabledUsers = User.findAllByEnabled(false)
        disabledUsers.each { user ->
            mailService.sendMail {
                to user.email
                subject "Account Status"
                body " Dear ${user.firstName},\n\nYour account is disabled, contact supprt for reactivation!"
            }

        }
    }
}
