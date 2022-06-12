import java.util.Comparator;
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

    public static Comparator<Video> vidViewComparator = new Comparator<Video>() { //compares all videos by their views
        @Override
        public int compare(Video o1, Video o2) {
            int views1 = o1.getViewCount();
            int views2 = o2.getViewCount();
            return views2-views1;
        }};
    public static Comparator<Video> vidLikeComparator = new Comparator<Video>() { //compares all videos by their likes
        @Override
        public int compare(Video o1, Video o2) {
            int likes1 = o1.getLikeCount();
            int likes2 = o2.getLikeCount();
            return likes2-likes1;
        }};
    public static Comparator<Video> vidCommComparator = new Comparator<Video>() { //compares all videos by their comment counts
        @Override
        public int compare(Video o1, Video o2) {
            int comms1 = o1.getCommentCount();
            int comms2 = o2.getCommentCount();
            return comms2-comms1;
        }};





    public String toString() {
        String s = "Title: " + getTitleString();
        s += "\n  * View Count: " + getViewCount();
        s += "\n  * Like Count: " + getLikeCount();
        s += "\n  * Comment Count: " + getCommentCount();
        s += "\n  * Link: https://youtu.be/"+ getVideoID();
        return s;
    }
}
