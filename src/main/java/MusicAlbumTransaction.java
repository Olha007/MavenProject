import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

public class MusicAlbumTransaction {

    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/music_album";
        final String DB_USER = "root";
        final String DB_PASSWORD = "77Ghfdj77";

        String insertAlbumSQL = "INSERT into album (album_id, album_name, release_year, genre_id) VALUES (?, ?, ?, ?)";
        String updateAlbumGenreSQL = "UPDATE album SET genre_id = ? WHERE album_id = ?";
        Savepoint albumInsertedSavePoint = null;

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD)) {
            System.out.println("Connected to the " + connection.getCatalog() + " database");

            try (PreparedStatement insertAlbumStmt = connection.prepareStatement(insertAlbumSQL);
                 PreparedStatement updateAlbumGenreStmt = connection.prepareStatement(updateAlbumGenreSQL)) {
                connection.setAutoCommit(false);

                insertAlbumStmt.setInt(1, 5); //album_id
                insertAlbumStmt.setString(2, "Black moon"); //album_name
                insertAlbumStmt.setInt(3, 2010); //release_year
                insertAlbumStmt.setInt(4, 2); //genre_id
                int rows = insertAlbumStmt.executeUpdate();
                System.out.println("\n " + rows + " record(s) inserted");

                albumInsertedSavePoint = connection.setSavepoint("Album inserted");

                updateAlbumGenreStmt.setInt(1, 3); //genre_id
                updateAlbumGenreStmt.setInt(2, 5); //album_id
                rows = updateAlbumGenreStmt.executeUpdate();
                System.out.println("\n " + rows + " record(s) updated");

                connection.commit();
                System.out.println("Transaction from the operations of adding an album" +
                        "and changing its genre has been successfully completed");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                if (albumInsertedSavePoint == null) {
                    connection.rollback();
                    System.out.println("Transaction rollback");
                } else {
                    connection.rollback(albumInsertedSavePoint);
                    System.out.println("Transaction rollback at albumInsertedSavePoint");
                    connection.commit();
                    System.out.println("The transaction from the operations of adding a album " +
                            "was successfully completed");
                }
            }

            connection.setAutoCommit(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

