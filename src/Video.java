public class Video extends Link {

    private String dateAdded;
    private int viewCount;
    private int commentCount;
    private int likeCount;

    //constructor
    public Video (int likes, int views, int comments, String link, String tS) {
        super(link,tS);
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

    //returns title String
    public String getTitleString() {
        return super.getTitleString();
    }

    //returns videoID
    public String getVideoID() {
        return super.getVideoID();
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
        String s = "Title: " + getTitleString();
        s += "\n  * View Count: " + getViewCount();
        s += "\n  * Like Count: " + getLikeCount();
        s += "\n  * Comment Count: " + getCommentCount();
        s += "\n  * Link: https://youtu.be/"+ getVideoID();
        return s;
    }
}
