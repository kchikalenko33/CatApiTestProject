package tests;

import db.DbConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseDbTest {
    @BeforeAll
    static void setupDb() {
        DbConnection.init(
                System.getenv("DB_URL"),
                System.getenv("DB_USER"),
                System.getenv("DB_PASSWORD"));

    }

    @AfterAll
    static void closeDb () {
        DbConnection.close();
    }
}
