package com.example;

import com.example.services.ProcessImageService;
import com.example.tables.ProcessImage;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImageJobHandler implements JobHandler {
    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
        // here: business logic that is executed with every job

        // get process variables
        final long instanceKey = job.getProcessInstanceKey();
        final String imageUrl = job.getVariablesAsMap().get("imageUrl").toString();

        // http request to provided url
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imageUrl))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();

        String ex = response.body();

        // store image response
        ProcessImageService processImageService = new ProcessImageService();

        ProcessImage processImage = new ProcessImage();
        processImage.setImageUrl(response.body());
        processImage.setProcessInstanceKey(Long.toString(instanceKey));

        processImageService.Insert(processImage);

        // complete process
        System.out.println(job);
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
