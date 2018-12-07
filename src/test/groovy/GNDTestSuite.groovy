import org.junit.AfterClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.moqui.Moqui
//list all the tests to be included in this suite

@RunWith(Suite.class) //always needed
@Suite.SuiteClasses([GNDServiceTests.class ]) 
class GNDTestSuite { 
    @AfterClass
    static void destroyMoqui() {
        println("END_SUITE")
        Moqui.destroyActiveExecutionContextFactory()
    }
}
