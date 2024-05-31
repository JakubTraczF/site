package music;

import database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public record Song(String artist, String title, int duration) {
    public static class Persistence {
        public static Optional<Song> read(int index) {
            try {
                String sql = "SELECT artist, title, duration FROM songs WHERE id = ?";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setInt(1, index);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return Optional.of(new Song(
                            resultSet.getString("artist"),
                            resultSet.getString("title"),
                            resultSet.getInt("duration")
                    ));
                } else {
                    return Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}