package org.example.mavenproject;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceDemo {

    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/music_album";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "77Ghfdj77";

    public static void main(String[] args) {
        /*Create config for data source*/
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_CONNECTION);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.addDataSourceProperty("poolName", "Hikari Connections Pool");
        config.addDataSourceProperty("maximumPoolSize", "15");
        config.addDataSourceProperty("minimumIdle", "2");
        /*Create data source*/
        HikariDataSource dataSource = new HikariDataSource(config);

        try (Connection connection = dataSource.getConnection()) {
            System.out.println("The " + connection.getCatalog() + " database is connected");
            System.out.println("Connection Pool: " + dataSource.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
