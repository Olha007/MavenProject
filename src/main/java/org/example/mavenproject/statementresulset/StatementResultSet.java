package org.example.mavenproject.statementresulset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementResultSet {
    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/music_album";
        final String DB_USER = "root";
        final String DB_PASSWORD = "77Ghfdj77";

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            System.out.println("The " + connection.getCatalog() + " database is connected");

            /* INSERT DATA */
            String strSQL = "INSERT INTO artist (artist_id, first_name, last_name, role_id) VALUES (4, 'Jane', 'Di', 1)";
            int rows = statement.executeUpdate(strSQL);
            System.out.println("\n " + rows + " record(s) inserted");

            /* READ DATA */
            System.out.println("Data read after insert:");
            readAllFromArtists(statement);

            /* UPDATE DATA */
            strSQL = "UPDATE artist SET first_name = 'Alex' WHERE artist_id = 4";
            rows = statement.executeUpdate(strSQL);
            System.out.println("\n " + rows + " record(s) updated");
            System.out.println("Data read after update:");
            readAllFromArtists(statement);

            /* DELETE DATA */
            strSQL = "DELETE FROM artist WHERE artist_id = 4";
            rows = statement.executeUpdate(strSQL);
            System.out.println("\n " + rows + " record(s) deleted");
            System.out.println("Data read after delete:");
            readAllFromArtists(statement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Метод для читання всіх даних з таблиці artist
    private static void readAllFromArtists(final Statement statement) throws SQLException {
        String strSQL = "SELECT * FROM artist";
        ResultSet resultSet = statement.executeQuery(strSQL);
        while (resultSet.next()) {
            int artistId = resultSet.getInt("artist_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            int roleId = resultSet.getInt("role_id");
            System.out.println("artist_id: " + artistId + ", first_name: " + firstName + ", last_name: " + lastName + ",role_id: " + roleId);
        }
    }
}
