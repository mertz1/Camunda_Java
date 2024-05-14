package com.example.camundaservice.services;


import com.example.camundaservice.database.JdbcConnection;
import com.example.camundaservice.database.tables.ProcessImage;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessImageService {

    //private final DynamoDbTable<ProcessImage> processImageTable;
    //private final String processImageTableName = "processImages";
    private static final Logger LOGGER =
            Logger.getLogger(ProcessImageService.class.getName());
    private final Optional<Connection> connection;

    public ProcessImageService() {
        this.connection = new JdbcConnection().getConnection();
    }

    public Optional<ProcessImage> getImage(long processInstanceKey) {
        return connection.flatMap(conn -> {
            Optional<ProcessImage> processImage = Optional.empty();
            String sql = "SELECT * FROM images WHERE processInstanceKey = " + processInstanceKey;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    // retrieve image
                    byte[] image = resultSet.getBytes("image");

                    processImage = Optional.of(
                            new ProcessImage(processInstanceKey, image));
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return processImage;
        });
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
