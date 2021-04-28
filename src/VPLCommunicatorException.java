import java.util.Arrays;
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



    public void setException(Throwable t){
        this.failureException = t;
    }

    public Throwable getException(){
        return this.failureException;
    }



    public boolean vplAssertEquals(String message, int points, Object... objects){

        for(int i = 0; i < objects.length; i++){
            for(int j = i; j < objects.length; j++){
                if(!objects[i].equals(objects[j])){
                    vplAssertTrue(message, points, false);
                    return false;
                }
            }
        }

        return vplAssertTrue(message, points, true);
    }

    public boolean vplAssertTrue(String message, int points, boolean result){
        if(result) {
            this.addPoints(points);

        }else{
            this.addComment(message);
        }
        return result;
    }

    public boolean vplAssertTrue(int points, boolean result){
        if(result) {
            this.addPoints(points);

        }

        return result;
    }

    public boolean vplAssertFalse(String message, int points, boolean result){
        return vplAssertTrue(message, points, !result);
    }

    public boolean vplAssertFalse(int points, boolean result){
        return vplAssertTrue(points, !result);
    }

    public boolean vplAssertEquals(String message, int points, int... array){
        if((array.length == 0) || (array.length == 1)){
            return true;
        }

        int temp = array[0];

        for(int i = 0; i < array.length; i++){
            if(temp != array[i]) {
                this.addComment(message);
                return false;
            }
        }

        this.addPoints(points);
        return true;
    }


    public void jUnitTestResult(){
        if(this.points >= this.maxPoints){
            assertTrue(true);
        }else{
            fail();
        }
    }


}
