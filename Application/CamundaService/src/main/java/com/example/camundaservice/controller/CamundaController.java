package com.example.camundaservice.controller;

import com.example.camundaservice.database.tables.ProcessImage;
import com.example.camundaservice.meta.ProcessConstants;
import com.example.camundaservice.meta.ProcessVariables;
import com.example.camundaservice.services.ProcessImageService;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping(value = "/camunda")
public class CamundaController {

    private final ZeebeClient zeebe;

    public CamundaController(final ZeebeClient zeebe) {
        this.zeebe = zeebe;
    }

    @PostMapping("/start")
    public long startProcessInstance(@RequestBody ProcessVariables variables) {
        final ProcessInstanceResult processInstanceResult =
                zeebe
                    .newCreateInstanceCommand()
                    .bpmnProcessId(ProcessConstants.BPMN_PROCESS_ID)
                    .latestVersion()
                    .variables(variables)
                    .withResult()
                    .send()
                    .join();

        return processInstanceResult.getProcessInstanceKey();
    }

    @GetMapping(value = "/image/{processInstanceKey}")
    public ResponseEntity<Object> getImage(@PathVariable long processInstanceKey) throws IOException {
        ProcessImageService imageService = new ProcessImageService();

        Optional<ProcessImage> processImage = imageService.getImage(processInstanceKey);

        if (processImage.isPresent()) {
            var imageBytes = processImage.get().getImage();
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bais);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] bytes = baos.toByteArray();

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/jpeg"))
                    .body(bytes);
        }

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}