public class Video extends Link {

    private String dateAdded;
    private int viewCount;
    private int commentCount;
    private int likeCount;

    //constructor
    public Video (int likes, int views, int comments, String link) {
        super(link);
        viewCount = views;
        commentCount = comments;
        likeCount = likes;
    }

    //getters

    //returns video title


    //returns view count
    public int getViewCount() {
        return viewCount;
    }

    //returns date added
    public String getDateAdded() {
        return dateAdded;
    }

    //returns like count
    public int getLikeCount() {
        return likeCount;
    }

    //returns comment count
    public int getCommentCount() {
        return commentCount;
    }

    public String toString() {
        String s = "";
        s += "  * View Count: " + getViewCount();
        s += "\n  * Like Count: " + getLikeCount();
        s += "\n  * Comment Count: " + getCommentCount();
        return s;
    }
}
