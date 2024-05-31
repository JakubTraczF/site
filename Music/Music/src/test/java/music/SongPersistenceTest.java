package music;

import database.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SongPersistenceTest {

    @BeforeEach
    public void connect() throws SQLException {
        DatabaseConnection.connect("songs.db");
    }

    @AfterEach
    public void disconnect() {
        DatabaseConnection.disconnect();
    }

    @Test
    public void testReadValidSong() {
        Optional<Song> song = Song.Persistence.read(1);
        assertTrue(song.isPresent(), "Song should be present");
        assertEquals("Artist1", song.get().artist(), "Artist1");
        assertEquals("Title1", song.get().title(), "Title1");
        assertEquals(300, song.get().duration(), "Dlugosc powinna miec 300 sekund");
    }

    @Test
    public void testReadInvalidSong() {
        Optional<Song> song = Song.Persistence.read(-1);
        assertFalse(song.isPresent(), "");
    }

    @ParameterizedTest
    @MethodSource("getSongData")
    public void testReadSongById(int index, String artist, String title, int duration) {
        Optional<Song> song = Song.Persistence.read(index);
        assertTrue(song.isPresent(), "");
        assertEquals(artist, song.get().artist(), artist);
        assertEquals(title, song.get().title(), title);
        assertEquals(duration, song.get().duration(), duration);
    }

    private static Stream<Arguments> getSongData() {
        return Stream.of(
                Arguments.of(1, "Artist1", "Title1", 300),
                Arguments.of(2, "Artist2", "Title2", 200),
                Arguments.of(3, "Artist3", "Title3", 240)
        );
    }

}