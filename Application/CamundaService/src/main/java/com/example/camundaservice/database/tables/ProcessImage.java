package com.example.camundaservice.database.tables;

public class ProcessImage {
    private long processInstanceKey;
    private byte[] image;

    public ProcessImage() {}

    public ProcessImage(long processInstanceKey, byte[] image) {
        this.processInstanceKey = processInstanceKey;
        this.image = image;
    }

    public long getProcessInstanceKey() { return this.processInstanceKey; }

    public void setProcessInstanceKey(long processInstanceKey) { this.processInstanceKey = processInstanceKey; }

    public byte[] getImage() { return this.image; }

    public void setImage(byte[] imageUrl) { this.image = imageUrl; }
}
