package com.example.tables;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class ProcessImage {
    private String processInstanceKey;
    private String imageUrl;

    @DynamoDbPartitionKey
    public String getProcessInstanceKey() { return this.processInstanceKey; }

    public void setProcessInstanceKey(String processInstanceKey) { this.processInstanceKey = processInstanceKey; }

    public String getImageUrl() { return this.imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
