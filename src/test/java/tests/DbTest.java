package tests;

import db.DbConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class DbTest extends BaseDbTest {
    @Test
    void connectDbTest() {
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("select count(*) from cats")) {

            rs.next();
            int count = rs.getInt(1);

            assertTrue(count > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
