import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class VPLCommunicatorException extends Throwable {
    private int points = 0;
    private int maxPoints = 100;
    private boolean failed = false;
    private LinkedList<String> comments = new LinkedList<>();

    private Throwable failureException = null;

    public void setPoints(int p) {
        // Make sure that points is always within [0, maxPoints]
        points = Math.max(0, Math.min(p, maxPoints));
    }

    public void addPoints(int p) {
        setPoints(points + p);
    }

    public void substractPoints(int p) {
        setPoints(points - p);
    }

    public void setMaxPoints(int mp) {
        maxPoints = mp;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getPoints() {
        return points;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public LinkedList<String> getComments() {
        return comments;
    }

    public boolean vplAssertTrue(String message, int points, boolean result){

        if(result) {
            this.addPoints(points);
//            System.out.println("--------------DEBUG: TRUE");

        }else{
            this.addComment(message);
//            fail();
//            System.out.println("--------------DEBUG: False; Message: " + message);
        }

        return result;
    }

    public void setException(Throwable t){
        this.failureException = t;
    }

    public Throwable getException(){
        return this.failureException;
    }

    public boolean vplAssertFalse(String message, int points, boolean result){
        return vplAssertTrue(message, points, !result);
    }

    public void jUnitTestResult(){
        if(this.points >= this.maxPoints){
            assertTrue(true);
        }else{
            fail();
        }
    }

//    public  boolean vplAssertEquals(String message, int points){
//        return vplAssertTrue(message, points, result);
//    }

//    public static void assertEquals(String message, String x, String y){
//        try {
//            AssassertEquals(message, x);
//        }catch (AssertionError ae){
//            System.out.println("-=EQUALS TEST MESSAGE=-");
//        }
//    }

//    public static void assertTrueX(String message, boolean x){
//        System.out.println("============================================================assertTrueX");
//        try {
//            Assert.assertTrue(message, x);
//        }catch (AssertionError ae){
//            System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
//        }
//    }
//
////    public int getPointsFinal() {
////        try{
////            return points;
////        }finally {
////            assertTrue("Y", false);
////        }
////
////    }
}
