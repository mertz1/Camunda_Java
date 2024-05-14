package com.example.services;


import com.example.database.JdbcConnection;
import com.example.tables.ProcessImage;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProcessImageService {

    //private final DynamoDbTable<ProcessImage> processImageTable;
    //private final String processImageTableName = "processImages";
    private final Optional<Connection> connection;

    public ProcessImageService() {
        this.connection = new JdbcConnection().getConnection();
    }

    public Optional<Integer> Insert(ProcessImage processImage) {
        String sql = "INSERT INTO "
                + "images(processInstanceKey, image)"
                + "VALUES (?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                    conn.prepareStatement(
                            sql,
                            PreparedStatement.RETURN_GENERATED_KEYS
                    )) {
                statement.setLong(1, processImage.getProcessInstanceKey());
                var temp = processImage.getImage();
                statement.setBinaryStream(2, new ByteArrayInputStream(processImage.getImage()));

                int numberOfInsertedRows = statement.executeUpdate();

                // Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return generatedId;
        });
    }
}
