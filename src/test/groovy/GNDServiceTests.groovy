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
        String donationAmount = "100"
        
        Map serviceCall = ec.service.sync().name("DonationPage.DonationPageServices.send#ConfirmationEmail").parameters([firstName: firstName, lastName: lastName, emailAddress: emailAddress, donationAmount: donationAmount]).call()
        println(serviceCall);

        then: 

        serviceCall.messageId != null;

        
        
        }

        //TO DO : 

        //***Create a Stripe Customer
            //create#StripeCustomer (email address, stripe token)
                //Then stripeCustomerId != null 

        //***Charge Customer 
             //charge#StripeCustomer (donationAmount, description)
                    //Then Charge.result != null 

        
        //**First Time Donor --> Create Monthly Donation 
            //create#OrderForMonthlyDonationPlan 
                //Then: ItemTypeEnumID = "ItemDonationMonthly"
                //      OrderStatusId = Approved (not complete)
                //      OrderPartTotal = donationAmount 
               //       customerPartyId = partyId
               //       stripeCustomerId = description 

        
        //Returning donor sets up Monthly Donation 
            //check#donorEmailAndFrequency (email, donationFrequency, _____party.Person info____)
           //Then queryForEmail != null 
                //stripeCustomerId = description
                //orderPartTotal = donationAmount
                //orderStatusId = approved
                //customerPartyId = partyId 
                                                                                                                                                                                                                                                                                                                                                                                                                                                 
        
        
        //*** Returning one-time donor charges customer even if clicked from first time donation 
                //#check#DonorEmailAndFrequency (email, donationFrequency, ___party.Person info___ )
                    //Then - stripeCustomerId = stripeCustomerId
                            //OrderPartyID = partyId
                            //OrderPart = complete 



        //***Test the CRON Service for processing Monthly Donations
            //create a monthly donor named CRON and check that his order ID/party Id was processed at a specific time today  
        
        
        //**CustomerId/Email stored from Stripe Object into Moqui
            //create#StripeCustomer 
                //Then: mantle.Party.Person.description = stripeCustomerId  
        
        
        //**Finalized Data Document for GND Donation Reports 
        
        
        //**Error handling for Stripe CC decline (page or message?)
                //please try again or use a different payment method?
        

}