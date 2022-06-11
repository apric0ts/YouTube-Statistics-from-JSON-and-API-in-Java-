public class Link {
    private String videoID;
    private String titleString;


    public Link (String vID,String tS) {
        videoID = vID;
        titleString=tS;

    }
    public Link() {
        videoID=null;
    }

    public String getVideoID() {
        return videoID;
    }
    public String getTitleString() {
        return titleString;
    }

}