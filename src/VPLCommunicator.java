public class VPLCommunicator {
    protected VPLCommunicatorException testResult;

    public void initializeTest() {
        testResult = new VPLCommunicatorException();
    }

    public VPLCommunicatorException getTestResult() {
        return testResult;
    }
}
