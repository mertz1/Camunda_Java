package com.example;

import com.example.services.ProcessImageService;
import com.example.tables.ProcessImage;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class ImageJobHandler implements JobHandler {
    @Override
    public void handle(final JobClient client, final ActivatedJob job) throws IOException {
        // here: business logic that is executed with every job

        // get process variables
        final long instanceKey = job.getProcessInstanceKey();
        final String imageUrl = job.getVariablesAsMap().get("imageUrl").toString();

        // http request to provided url
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] bytes = baos.toByteArray();


        // store image response
        ProcessImageService processImageService = new ProcessImageService();

        ProcessImage processImage = new ProcessImage();
        processImage.setImage(bytes);
        processImage.setProcessInstanceKey(instanceKey);

        processImageService.Insert(processImage);

        // complete process
        System.out.println(job);
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
