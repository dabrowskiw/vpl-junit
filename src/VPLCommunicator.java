import java.util.LinkedList;

public class VPLCommunicator {
    protected VPLCommunicatorException testResult;
    protected LinkedList<VPLSubTask> subTaskList;

    public void initializeTest() {
        this.subTaskList = new LinkedList<VPLSubTask>();

        this.testResult = new VPLCommunicatorException();
    }


    public VPLCommunicatorException getTestResult() {
        return testResult;
    }

    public void addNewSubTest(String comment, int points){
        subTaskList.add(new VPLSubTask(comment, points));
        getTestResult().addPoints(points);
//        VPLCommunicatorException vplException = new VPLCommunicatorException();
//        vplException.addComment(comment);
//        vplException.addPoints(points);
//        this.exceptionList.add(vplException);
    }

    public int reCountPoints(){
        int result = 0;

        for(VPLSubTask o: subTaskList){
            result += o.getPoints();
        }

        getTestResult().setPoints(result);

        return result;
    }
}
