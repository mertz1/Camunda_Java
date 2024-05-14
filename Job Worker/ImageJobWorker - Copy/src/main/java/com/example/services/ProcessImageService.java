package com.example.services;


import com.example.tables.ProcessImage;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

public class ProcessImageService {

    private final DynamoDbTable<ProcessImage> processImageTable;
    private final String processImageTableName = "processImages";

    public ProcessImageService() {
        DynamoDbClient standardClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create("http://localhost:8000"))
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(standardClient)
                .build();

        this.processImageTable = enhancedClient.table(processImageTableName, TableSchema.fromBean(ProcessImage.class));
    }

    public void Insert(ProcessImage processImage) {
        this.processImageTable.putItem(processImage);
    }
}
