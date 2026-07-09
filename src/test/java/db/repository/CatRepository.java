package db.repository;

import db.DbConnection;
import db.entity.CatEntity;
import testData.Color;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CatRepository {
    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS cats (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                color VARCHAR(50),
                age INT
            )
            """;
    private static final String GET_CAT = """
            SELECT *
                        FROM cats
                        WHERE id = ?
            """;

    public void createCatTable() throws SQLException {
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE);
        }
    }

    public CatEntity getCatById(Integer id) throws SQLException {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CAT)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapToCatEntity(rs);
            }
        }
    }

    private CatEntity mapToCatEntity(ResultSet rs) throws SQLException {
        String color = rs.getString("color");

        return new CatEntity(
                getIntOrNull(rs, "id"),
                rs.getString("name"),
                rs.getObject("age", Integer.class),
                color == null ? null : Color.valueOf(color),
                getIntOrNull(rs, "breed_id"),
                getIntOrNull(rs, "owner_id"),
                rs.getObject("weight", Double.class),
                rs.getObject("vaccinated", Boolean.class),
                rs.getObject("birth_date", LocalDate.class),
                rs.getString("status"),
                rs.getObject("created_at", LocalDateTime.class),
                rs.getObject("updated_at", LocalDateTime.class)
        );
    }

    private Integer getIntOrNull(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : value;
    }

}
