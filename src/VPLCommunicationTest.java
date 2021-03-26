import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(VPLCommunicationRunner.class)
public class VPLCommunicationTest extends VPLCommunicator {
    @Test
    public void simplePointsTest_25P() {
//        super.testResult = null;
//        getTestResult().setPoints(25);
//        getTestResult().addComment("Blub");

        getTestResult().vplAssertTrue("Blub",  10, true);
        getTestResult().vplAssertTrue("Blub2", 15, true);

//        assertTrue("DFGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG", false);
        getTestResult().jUnitTestResult();
//        assertTrue(true);
    }

    @Test
    public void simplePointsTest2_26P() {
//        super.testResult = null;
//        getTestResult().setPoints(25);
//        getTestResult().addComment("Blub");

        getTestResult().vplAssertTrue("Blub",  10, true);
        getTestResult().vplAssertTrue("Blub2", 10, false);
        getTestResult().vplAssertTrue("Blub2", 1, true);
//        assertTrue("DFGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG", false);
//        getTestResult().jUnitTestResult();
//        assertTrue(true);
    }

}




//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.junit.Assert.assertTrue;
//
//@RunWith(VPLCommunicationRunner.class)
//public class VPLCommunicationTest extends VPLCommunicator {
//
//    @Test
//    public void simplePointsTest_25P() throws VPLCommunicatorException {
//        getTestResult().setPoints(10);
//        getTestResult().addComment("Blub");
//
////        getTestResult().assertTrueX("XYZ", false);
//        assertTrue("XYZ", false);
//
////        Assert
////        throw getTestResult();
////        getTestResult().assertTrue("X", false);
////        getTestResult().addPoints(10);
////        getTestResult().addComment("Blub");
////        getTestResult().assertTrue("Y", true);
//
////        getTestResult().setPoints(5);
////        getTestResult().addComment("Blub");
////        assertTrue("Z", false);
//    }
//
//    @Test
//    public void simplePointsTest2_30P() {
//        getTestResult().setPoints(22);
//        getTestResult().addComment("Bling");
//        assertTrue("XYZ", true);
//    }
//}
