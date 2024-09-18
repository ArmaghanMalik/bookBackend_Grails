package bookBackend

class DisabledUserEmailJob {
EmailService emailService
    def mailService
    static triggers = {
           cron name: 'dailyTrigger', cronExpression: "1 */1 * * * ?"
    }

    def execute() {
//         emailService.sendDailyEmailToDisabledUsers()
    }
}
