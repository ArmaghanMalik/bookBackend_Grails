//package bookBackend
//
//import com.examples.wsdl.showpurchase.GetPurchaseRequest
//import com.examples.wsdl.showpurchase.GetPurchaseResponse
//import com.examples.wsdl.showpurchase_wsdl.ShowPurchasePortType
//
//import com.examples.wsdl.showpurchase.Book
//import com.examples.wsdl.showpurchase.User
//import com.examples.wsdl.showpurchase.BookList
//import grails.gorm.transactions.Transactional
//import org.apache.cxf.interceptor.InInterceptors
//import org.apache.cxf.jaxws.JaxWsServerFactoryBean
//
////import org.apache.cxf.interceptor.InInterceptors
//import org.grails.cxf.utils.GrailsCxfEndpoint
//import javax.jws.WebParam
//
//@InInterceptors(classes = AuthenticateInterceptor.class)
//@GrailsCxfEndpoint()
//@Transactional
//class UserDetailsSoapService implements ShowPurchasePortType {
//    UserDetailsSoapService(){
//            //without this the service becomes unavailable
//            JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean()
//            factory.serviceBean = this
//            factory.address = "/UserDetailsSoap"
//            factory.serviceClass = ShowPurchasePortType
//            factory.getInInterceptors().add(new AuthenticateInterceptor())
//            factory.create()
//    }
//    @Override
//    GetPurchaseResponse showPurchase(@WebParam(partName = "parameters", name = "getPurchaseRequest", targetNamespace = "http://www.examples.com/wsdl/showPurchase.wsdl") GetPurchaseRequest parameters) {
//        GetPurchaseResponse response = new GetPurchaseResponse()
//
//        def email = parameters.email
//        if (!email) {
//            response.responseCode = 400
//            response.responseDescription = "Invalid Email"
//            return response
//        }
//
//        def user = new bookBackend.User()
//        user = bookBackend.User.findByEmail(email)
//
//        if (!user) {
//            response.responseCode = 400
//            response.responseDescription = "User not found"
//            return response
//        }
//
//        Purchase purchases = new Purchase()
//        purchases = Purchase.findAllByUser(user)  //purchases
//        if (!purchases || purchases.isEmpty()) {
//            response.responseCode = 400
//            response.responseDescription = "No purchases found for the user"
//            return response
//        }
//
//        def ruser = new User()
//        ruser.firstName = user.firstName
//        ruser.lastName = user.lastName
//        ruser.phone = user.phone as int
//        ruser.username = user.username
//
//        def bookList = new BookList()
//        purchases.each { purchase ->
//            def rbook = new Book()
//            rbook.title = purchase.book.title
//            rbook.author = purchase.book.author
//            rbook.isbn = purchase.book.isbn as int
//            rbook.price = (long) purchase.book.price
//            bookList.book.add(rbook)
//        }
//        response.user = ruser
//        response.list = bookList
//        response.responseCode = 200
//        response.responseDescription = "User and books retrieved successfully"
//        return response
//    }
//}
