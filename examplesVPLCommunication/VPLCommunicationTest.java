import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VPLCommunicationRunner.class)
public class VPLCommunicationTest extends VPLCommunicator {
    @Test
    public void simplePointsTest_25P() {
        getTestResult().setPoints(10);
        getTestResult().addComment("Blub");

    }
}
