public class VPLSubTask {
    private String comment;
    private int points;


    public VPLSubTask(String comment, int points){

        this.setComment(comment);
        this.setPoints(points);
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


}
