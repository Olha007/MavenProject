import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatement {

    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/music_album";
        final String DB_USER = "root";
        final String DB_PASSWORD = "77Ghfdj77";

        String insertAlbumSQL = "INSERT into album (`album_id`, `album_name`, `release_year`, `genre_id`) VALUES (?, ?, ?, ?)";
        String updateAlbumGenreSQL = "UPDATE album SET genre_id = ? WHERE album_id = ?";
        String selectAlbumSQL = "SELECT * FROM album WHERE album_id = ?";
        String deleteAlbumSQL = "DELETE FROM album where album_id = ?";
        String selectAllAlbumsSQL = "SELECT * FROM album";

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
             java.sql.PreparedStatement insertAlbumStmt = connection.prepareStatement(insertAlbumSQL);
             java.sql.PreparedStatement updateAlbumGenreStmt = connection.prepareStatement(updateAlbumGenreSQL);
             java.sql.PreparedStatement selectAlbumStmt = connection.prepareStatement(selectAlbumSQL);
             java.sql.PreparedStatement deleteAlbumStmt = connection.prepareStatement(deleteAlbumSQL);
             java.sql.PreparedStatement selectAllAlbumsStmt = connection.prepareStatement(selectAllAlbumsSQL)) {
            System.out.println("Connected to the " + connection.getCatalog() + " database");

            /*Підготовка даних для вставки*/
            insertAlbumStmt.setInt(1, 4);
            insertAlbumStmt.setString(2, "Savage Mode"); //album_name
            insertAlbumStmt.setInt(3, 2024); // release_year
            insertAlbumStmt.setInt(4, 1); //genre_id
            int rows = insertAlbumStmt.executeUpdate();
            System.out.println("\n " + rows + " record(s) inserted");
            readAllFromAlbums(selectAllAlbumsStmt);

            /*Підготовка даних для оновлення*/
            updateAlbumGenreStmt.setInt(1, 2);
            updateAlbumGenreStmt.setInt(2, 4);
            rows = updateAlbumGenreStmt.executeUpdate();
            System.out.println("\n " + rows + " record(s) updated");

            System.out.println("Data read after insert and update:");
            selectAlbumStmt.setInt(1, 4); // Ваші дані для album_id
            ResultSet rs = selectAlbumStmt.executeQuery();
            rs.next();
            System.out.println("album_id: " + rs.getInt(1) + ", album_name: " + rs.getString(2)
                    + ", release_year: " + rs.getInt(3) + ", genre_id: " + rs.getInt(4));

            /*Підготовка даних для видалення*/
            deleteAlbumStmt.setInt(1, 4); //для album_id
            /*Видалення даних*/
            rows = deleteAlbumStmt.executeUpdate();
            System.out.println("\n " + rows + " record(s) deleted");
            System.out.println("Data read after insert and delete:");
            readAllFromAlbums(selectAllAlbumsStmt);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void readAllFromAlbums(final java.sql.PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int albumId = resultSet.getInt("album_id");
            String albumName = resultSet.getString("album_name");
            int releaseYear = resultSet.getInt("release_year");
            int genreId = resultSet.getInt("genre_id");
            System.out.println("album_id: " + albumId + ", album_name: " + albumName +
                    ", release_year: " + releaseYear + ", genre_id: " + genreId);
        }
    }
}
