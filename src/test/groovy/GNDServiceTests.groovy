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
    
    //##COMPLETE
    //def "Sends an email to user when service is called " (){
    //     when:
    //     String firstName = "Ryan"
    //     String lastName = "Higgins"
    //     String emailAddress = "rhiggins32@gmail.com"
    //     String donationAmount = "100"
        
    //     Map serviceCall = ec.service.sync().name("DonationPage.DonationPageServices.send#ConfirmationEmail").parameters([firstName: firstName, lastName: lastName, emailAddress: emailAddress, donationAmount: donationAmount]).call()
    //     println(serviceCall);

    //     then: 

    //     serviceCall.messageId != null;
    // }
    //####COMPLETE
    // def "Creates a Stripe Customer when donation is submitted" (){
    //     when:
    //     String firstName = "Ryan"
    //     String lastName = "Higgins"
    //     String emailAddress = "rhiggins32@gmail.com"
    //     String stripeToken = "tok_visa"
    //     String donationAmount = "100"
        
    //     Map serviceCall = ec.service.sync().name("DonationPage.DonationPageServices.create#StripeCustomer").parameters([firstName: firstName, lastName: lastName, emailAddress: emailAddress, donationAmount: donationAmount, stripeToken: stripeToken]).call()
    //     println(serviceCall);

    //     then: 

    //     serviceCall.stripeCustomerId != null;
    // }

    // //###COMPLETE

    //  def "Charges correct Stripe Customer when donation is submitted" (){
    //     when:
    //     String firstName = "Ryan"
    //     String lastName = "Higgins"
    //     String emailAddress = "rhiggins32@gmail.com"
    //     String stripeCustomerId = "cus_E8CuqtHMk4l8hH"
    //     String donationAmount = "100"
        
    //     Map serviceCall = ec.service.sync().name("DonationPage.DonationPageServices.charge#StripeCustomer").parameters([firstName: firstName, lastName: lastName, donationAmount: donationAmount, description: stripeCustomerId ]).call()
    //     println(serviceCall);

    //     then: 

    //     serviceCall.paid != null;
    // }

}




        //TO DO : 

        //***Create a Stripe Customer #####COMPLETED
            //create#StripeCustomer (email address, stripe token)
                //Then stripeCustomerId != null 
                

        //***Charge Customer 
             //charge#StripeCustomer (donationAmount, description)
                    //Then Charge.result != null 

        
        //****Create Monthly Donation 
            //create#OrderForMonthlyDonationPlan 
                //Then: ItemTypeEnumID = "ItemDonationMonthly"
                //      OrderStatusId = Approved (not complete)
                //      OrderPartTotal = donationAmount 
               //       customerPartyId = partyId
               //       stripeCustomerId = description 
                                                                                                                                                                                                                                                                                                                                                                                                     
        
        
        //*** Returning one-time donor charges customer even if clicked from first time donation 
                //#check#DonorEmailAndFrequency (email, donationFrequency, ___party.Person info___ )
                    //Then - stripeCustomerId = stripeCustomerId
                            //OrderPartyID = partyId
                            //OrderPart = complete 



        //***Test the CRON Service for processing Monthly Donations
            //create a monthly donor named CRON and check that his order ID/party Id was processed at a specific time today  
        
        
        
        //**Finalized Data Document for GND Donation Reports 
        
        
        //**Error handling for Stripe CC decline (page or message?)
                //please try again or use a different payment method?
        

