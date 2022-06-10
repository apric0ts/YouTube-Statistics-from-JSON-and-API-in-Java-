public class Link {
    private String videoID;
    private String videoTitle;
    private String channelID;

    public Link (String vID, String vTitle, String cName) {
        videoID = vID;
        videoTitle = vTitle;
        channelID = cName;
    }
    public Link() {
        videoID=null;
        videoTitle=null;
        channelID=null;
    }

    public String getVideoID() {
        return videoID;
    }
    public String getVideoTitle() {
        return videoTitle;
    }
    public String getChannelID() {
        return channelID;
    }
}