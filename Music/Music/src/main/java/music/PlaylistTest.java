package music;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {

    @Test
    public void testNewPlaylistIsEmpty() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty(), "Playlista pusta");
    }

    @Test
    public void testAddSongIncreasesSize() {
        Playlist playlist = new Playlist();
        Song song = new Song("Artist", "Title", 300);
        playlist.add(song);
        assertEquals(1, playlist.size(), "Rozmiar Playlisty powinien byc 1");
    }

    @Test
    public void testAddSongContainsSong() {
        Playlist playlist = new Playlist();
        Song song = new Song("Artist", "Title", 300);
        playlist.add(song);
        assertTrue(playlist.contains(song), "Playlista powinna miec piosenke");
    }

    @Test
    public void testAddSongAndRetrieve() {
        Playlist playlist = new Playlist();
        Song song = new Song("Artist", "Title", 300);
        playlist.add(song);
        assertEquals(song, playlist.get(0), "Playlista powinna miec piosenke");
    }
 @Test
public void testAtSecond() {
    Playlist playlist = new Playlist();
    Song song1 = new Song("Artist1", "Title1", 300);
    Song song2 = new Song("Artist2", "Title2", 200);
    playlist.add(song1);
    playlist.add(song2);
    assertEquals(song1, playlist.atSecond(100), "1");
    assertEquals(song2, playlist.atSecond(350), "2");
}

@Test
public void testAtSecondThrowsExceptionForTimeOutOfBounds() {
    Playlist playlist = new Playlist();
    Song song = new Song("Artist", "Title", 300);
    playlist.add(song);
    assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(400), "IndexOutOfBoundsException");
}

@Test
public void testAtSecondThrowsExceptionForNegativeTime() {
    Playlist playlist = new Playlist();
    Song song = new Song("Artist", "Title", 300);
    playlist.add(song);
    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-50), "IndexOutOfBoundsException");
    assertTrue(exception.getMessage().contains("Negative time"), "Negative time");
}
}