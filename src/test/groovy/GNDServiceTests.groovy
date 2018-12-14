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

        // we still have to disableAuthz even though a user is logged in because this user does not have permission to call this service directly (normally is called through a screen with inherited permission)

        ec.artifactExecution.disableAuthz()
        ec.transaction.begin(null)
    }

    def cleanup() {
        ec.transaction.commit()
        ec.artifactExecution.enableAuthz()
        ec.user.logoutUser()
    }

    def "Sends an email to user when service is called " (){
        when:
        String firstName = "Young"
        String lastName = "Hsu"
        String emailAddress = "justin1020@gmail.com"
        String donationAmount = "100"
        
        Map serviceCall = ec.service.sync().name("DonationPage.DonationPageServices.send#ConfirmationEmail").parameters([firstName: firstName, lastName: lastName, emailAddress: emailAddress, donationAmount: donationAmount]).call()
        println(serviceCall);

        then: 
        serviceCall.messageId != null;
        }

    def "Creates an order with the OrderItem 'ItemDonationMonthly' when a user decides to set up monthly donation plan" (){
        when:
        String emailAddress = "test@test.com"
        String donationAmount = "100"
        String donationFrequency = "monthly-donation"
        String firstName = "test_firstName"
        String lastName = "test_lastName"
        String contactNumber = "1231231234"
        String address1 = "Te St."
        String city = "Test"
        String stateProvinceGeoId = "USA_CA"
        String postalCode = "92122"
        String stripeToken = "tok_visa"
        
        ec.service.sync().name("DonationPage.DonationPageServices.check#DonorEmailAndFrequency").parameters([emailAddress: emailAddress, donationAmount: donationAmount, donationFrequency: donationFrequency, firstName: firstName, lastName: lastName, contactNumber: contactNumber, address1: address1, city: city, stateProvinceGeoId: stateProvinceGeoId, postalCode: postalCode, stripeToken: stripeToken]).call()

        EntityValue testContactMech = ec.entity.find("mantle.party.contact.ContactMech").condition( [ "infoString" : emailAddress ] ).one()
        EntityValue testPartyContactMech = ec.entity.find("mantle.party.contact.PartyContactMech").condition( [ "contactMechId" : testContactMech.contactMechId ] ).one()
        EntityValue testOrderPart = ec.entity.find("mantle.order.OrderPart").condition( [ "customerPartyId" : testPartyContactMech.partyId ] ).one()
        EntityValue testOrderItem = ec.entity.find("mantle.order.OrderItem").condition( [ "orderId" : testOrderPart.orderId ] ).one()

        then: 
        testOrderPart.statusId == "OrderApproved"
        testOrderPart.partTotal.toString() == donationAmount
        testOrderItem.itemTypeEnumId == "ItemDonationMonthly"
    }

    def "process#MonthlyDonation will create donations for ALL users who've set up a monthly donation plan" (){
        when:

        ec.service.sync().name("DonationPage.DonationPageServices.check#DonorEmailAndFrequency").parameters([emailAddress: "test@test.com", donationAmount: "100", donationFrequency: "monthly-donation", firstName: "test_firstName", lastName: "test_lastName", contactNumber: "1231231234", address1: "Te St.", city: "Test", stateProvinceGeoId: "USA_CA", postalCode: "92122", stripeToken: "tok_visa"]).call()

        ec.service.sync().name("DonationPage.DonationPageServices.check#DonorEmailAndFrequency").parameters([emailAddress: "test2@test.com", donationAmount: "200", donationFrequency: "monthly-donation", firstName: "test2_firstName", lastName: "test2_lastName", contactNumber: "1231231234", address1: "2 Te St.", city: "Test2", stateProvinceGeoId: "USA_CA", postalCode: "92122", stripeToken: "tok_visa"]).call()

        ec.service.sync().name("DonationPage.DonationPageServices.process#MonthlyDonation").call()

        EntityValue testContactMech = ec.entity.find("mantle.party.contact.ContactMech").condition( [ "infoString" : "test@test.com" ] ).one()
        EntityValue testPartyContactMech = ec.entity.find("mantle.party.contact.PartyContactMech").condition( [ "contactMechId" : testContactMech.contactMechId ] ).one()
        EntityValue testOrderPart = ec.entity.find("mantle.order.OrderPart").condition( [ "customerPartyId" : testPartyContactMech.partyId, "statusId" : "OrderCompleted"] ).one()
        EntityValue testOrderItem = ec.entity.find("mantle.order.OrderItem").condition( [ "orderId" : testOrderPart.orderId ] ).one()

        EntityValue test2ContactMech = ec.entity.find("mantle.party.contact.ContactMech").condition( [ "infoString" : "test2@test.com" ] ).one()
        EntityValue test2PartyContactMech = ec.entity.find("mantle.party.contact.PartyContactMech").condition( [ "contactMechId" : test2ContactMech.contactMechId ] ).one()
        EntityValue test2OrderPart = ec.entity.find("mantle.order.OrderPart").condition( [ "customerPartyId" : test2PartyContactMech.partyId, "statusId" : "OrderCompleted"] ).one()
        EntityValue test2OrderItem = ec.entity.find("mantle.order.OrderItem").condition( [ "orderId" : test2OrderPart.orderId ] ).one()

        then: 
        testOrderPart.statusId=="OrderCompleted"
        testOrderPart.partTotal.toString() == "100"
        testOrderItem.itemTypeEnumId == "ItemDonation"
        test2OrderPart.partTotal.toString() == "200"
        test2OrderItem.itemTypeEnumId == "ItemDonation"
    }

    def "Creates a Stripe Customer when donation is submitted" (){
        when:
        String firstName = "Ryan"
        String lastName = "Higgins"
        String emailAddress = "rhiggins32@gmail.com"
        String stripeToken = "tok_visa"
        String donationAmount = "100"
        
        Map serviceCall = ec.service.sync().name("DonationPage.DonationPageServices.create#StripeCustomer").parameters([firstName: firstName, lastName: lastName, emailAddress: emailAddress, donationAmount: donationAmount, stripeToken: stripeToken]).call()
        println(serviceCall);

        then: 
        serviceCall.stripeCustomerId != null;
    }

    
    def "Charges correct Stripe Customer when donation is submitted" (){
        when:
        Map serviceCall = ec.service.sync().name("DonationPage.DonationPageServices.create#StripeCustomer").parameters([firstName: "test5_firstName", lastName: "test5_lastName", emailAddress: "test5@test.com",stripeToken: "tok_visa"]).call()
        
        Map serviceCall2 = ec.service.sync().name("DonationPage.DonationPageServices.charge#StripeCustomer").parameters([donationAmount: "100", description: serviceCall.stripeCustomerId ]).call()
        println(serviceCall2);

        

        then: 
        serviceCall2.paid != null;
    }

}