import java.util.ArrayList;
public class Playlist {
    private ArrayList<Video> list = new ArrayList<Video>();
    public Playlist() {
        list = null;
    }


    //adds video to playlist
    public void addVideo(Video vid) {
        list.add(vid);
    }

    //removes video at specified location
    public void removeVideo(int position) {
        list.remove(position);
    }

    //prints playlist
    public void printPlaylist() {
        for (int i = 0; i<list.size(); i++) {
            //System.out.println("Video " + i + ": " + list.get(i).getVideoTitle());
            System.out.println("View Count " + i + ": " + list.get(i).getViewCount());
            System.out.println("Like Count: " + list.get(i).getLikeCount());
            System.out.println("-------------------------------------------");
        }
    }
}
