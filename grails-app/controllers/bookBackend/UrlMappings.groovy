package bookBackend

class UrlMappings {
    static mappings = {

        "/"(controller: "login", action: "index")
        "/login"(controller: "login", action: "index")
        "/logout"(controller: "login", action: "logout")
        "/login/authenticate"(controller: "login", action: "login")

        "/admin"(controller: 'admin', action: 'index')
        "/admin/removeBook"(controller: "admin", action: "removeBook")
        "/admin/removeUser"(controller: "admin", action: "removeUser")
        "/addUser"(controller: "admin", action: "addUser")
        "/addBook"(controller: "admin", action: "addBook")
        "/updateBookInfo"(controller: "admin", action: "updateBookInfo")
        "/updateUserInfo"(controller: "admin", action: "updateUserInfo")

        "/dashboard"(controller: "user", action: "dashboard")
        "/user/changePassword"(controller: "user", action: "changePassword")
        "/user/purchaseBook/$id?"(controller: "user", action: "purchaseBook")
        "/user/viewBook/$id?"(controller: "user", action: "viewBook")
        "500"(view:'/error')
        "404"(view:'/notFound')
        }
}
