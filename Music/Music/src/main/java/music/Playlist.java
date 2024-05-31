package music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int seconds) {
        if (seconds < 0) {
            throw new IndexOutOfBoundsException(seconds);
        }
        int currentTime = 0;
        for (Song song : this) {
            currentTime += song.duration();
            if (seconds < currentTime) {
                return song;
            }
        }
        throw new IndexOutOfBoundsException(seconds);
    }
}