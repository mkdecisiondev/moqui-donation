import org.moqui.Moqui
import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityValue
import org.moqui.entity.EntityList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.moqui.screen.ScreenTest
import org.moqui.screen.ScreenTest.ScreenTestRender
import spock.lang.*

class GNDServiceTests extends Specification {
    protected final static Logger logger = LoggerFactory.getLogger(GNDServiceTests.class)

    @Shared
    ExecutionContext ec

    def setupSpec() {
        // init the framework, get the ec
        ec = Moqui.getExecutionContext()
        // ec.user.loginUser("john.doe", "moqui")
    }

    def cleanupSpec() {
        ec.destroy()
    }

    def setup() {
        ec.user.loginUser("john.doe", "moqui")
        // we still have to disableAuthz even though a user is logged in because this user does not have permission to
        //     call this service directly (normally is called through a screen with inherited permission)
        ec.artifactExecution.disableAuthz()
        ec.transaction.begin(null)
    }

    def cleanup() {
        ec.transaction.commit()
        ec.artifactExecution.enableAuthz()
        ec.user.logoutUser()
    }
    // def "Create UUID generates random code"(){
    //     when:
    //     Map serviceCall= ec.service.sync().name("SEStest.SEStestServices.create#id").call()
    //     println(serviceCall);

    //     then:
    //     println("####################### TESTING RESULTS ##################")
    //     serviceCall.verifyCode.size() == 36 
       
    // }
    def "Sends an email to user when service is called " (){
        when:
        String firstName = "Ryan"
        String lastName = "Higgins"
        String emailAddress = "rhiggins32@gmail.com"
        String donation = "100"
        
        Map serviceCall = ec.service.sync().name("DonationPage.DonationPageServices.send#ConfirmationEmail").parameters([firstName: firstName, lastName: lastName, emailAddress: emailAddress, DonationAmount: donation]).call()
        println(serviceCall);

        then: 

        serviceCall.messageId != null;

        
        
        }

}