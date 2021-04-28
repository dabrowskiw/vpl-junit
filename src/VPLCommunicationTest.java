import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(VPLCommunicationRunner.class)
public class VPLCommunicationTest extends VPLCommunicator {
//    @Test
//    public void simplePointsTest_25P() {
////        super.testResult = null;
////        getTestResult().setPoints(25);
////        getTestResult().addComment("Blub");
//
//        getTestResult().vplAssertTrue("Blub",  10, true);
//        getTestResult().vplAssertTrue("Blub2", 15, true);
//
//
//        getTestResult().jUnitTestResult();
////        assertTrue(true);
//    }

    @Test
    public void simplePointsTest2_26P() {
//        super.testResult = null;
//        getTestResult().setPoints(25);
//        getTestResult().addComment("Blub");

//        getTestResult().vplAssertTrue("Blub",  10, true);
//        getTestResult().vplAssertTrue("Blub2", 10, false);
//        getTestResult().vplAssertTrue("Blub2", 1, true);
        getTestResult().vplAssertEquals("", 5, "X", "C");

//        getTestResult().jUnitTestResult();
//        assertTrue(true);
    }


}