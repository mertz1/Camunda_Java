package com.example;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.ZeebeClientBuilder;
import io.camunda.zeebe.client.api.worker.JobWorker;

import java.time.Duration;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        final String defaultAddress = "localhost:26500";
        final String envVarAddress = System.getenv("ZEEBE_ADDRESS");

        final ZeebeClientBuilder clientBuilder;
        if (envVarAddress != null) {
            /* Connect to Camunda Cloud Cluster, assumes that credentials are set in environment variables.
             * See JavaDoc on class level for details
             */
            clientBuilder = ZeebeClient.newClientBuilder().gatewayAddress(envVarAddress);
        } else {
            // connect to local deployment; assumes that authentication is disabled
            clientBuilder = ZeebeClient.newClientBuilder().gatewayAddress(defaultAddress).usePlaintext();
        }

        final String jobType = "getImage";

        try (final ZeebeClient client = clientBuilder.build()) {
            System.out.println("Opening job worker.");

            try (final JobWorker workerRegistration =
                         client
                                 .newWorker()
                                 .jobType(jobType)
                                 .handler(new ImageJobHandler())
                                 .timeout(Duration.ofSeconds(10))
                                 .open()) {
                System.out.println("Job worker opened and receiving jobs.");

                // run until System.in receives exit command
                waitUntilSystemInput("exit");
            }
        }
    }

    private static void waitUntilSystemInput(final String exitCode) {
        try (final Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                final String nextLine = scanner.nextLine();
                if (nextLine.contains(exitCode)) {
                    return;
                }
            }
        }
    }
}