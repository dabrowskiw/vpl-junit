import java.util.LinkedList;

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
}
