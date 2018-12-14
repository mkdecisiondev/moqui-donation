// import org.moqui.Moqui
// import org.moqui.context.ExecutionContext
// import org.moqui.entity.EntityValue
// import org.moqui.entity.EntityList
// import org.slf4j.Logger
// import org.slf4j.LoggerFactory
// import org.moqui.screen.ScreenTest
// import org.moqui.screen.ScreenTest.ScreenTestRender
// import spock.lang.*

// class GNDScreenTests extends Specification { 
//     protected final static Logger logger = LoggerFactory.getLogger(GNDScreenTests.class)

//     @Shared
//     ExecutionContext ec
    
//     @Shared ScreenTest screenTest

//     def setupSpec() {
//         // init the framework, get the ec
//         ec = Moqui.getExecutionContext()
//         ec.user.loginUser("john.doe", "moqui")
//         screenTest = ec.screen.makeTest().baseScreenPath("apps")
//     }

//     def cleanupSpec() {
//         long totalTime = System.currentTimeMillis() - screenTest.startTime
//         logger.info("Rendered ${screenTest.renderCount} screens (${screenTest.errorCount} errors) in ${ec.l10n.format(totalTime/1000, "0.000")}s, output ${ec.l10n.format(screenTest.renderTotalChars/1000, "#,##0")}k chars")

//         ec.destroy()
//     }

//     def setup() {
//         // ec.user.loginUser("john.doe", "moqui")
//         // we still have to disableAuthz even though a user is logged in because this user does not have permission to
//         //     call this service directly (normally is called through a screen with inherited permission)
//         ec.artifactExecution.disableAuthz()
//         ec.transaction.begin(null)
//     }

//     def cleanup() {
//         ec.transaction.commit()
//         ec.artifactExecution.enableAuthz()
//         ec.user.logoutUser()
//     }

//     // //Testing screen rendering correctly with its form
//     // // def "render FirstTimeDonor screen"() {
//     // //     when: 
//     // //     String screenPath = "SEStest" //path where form is 
//     // //     String formTitle = "SEStest" //title for the form that's visible on the actual webpage

//     //     ScreenTestRender str = screenTest.render(screenPath, null, null)
//     //     String htmlText = str.output

//     //     logger.info("Rendered ${screenPath} in ${str.getRenderTime()}ms, ${htmlText.length()} characters")

//     //     then:
//     //     screenTest.errorCount == 0
//     //     htmlText.contains(formTitle)
//     // }
// }


//TODO

//**First Time Donor Screen
//**Returning Donor Screen
//**Thank You Page Screen
//** Test that Stripe Card Element renders on each donation page 
