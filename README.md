# Camunda_Java
A sample camunda project built in Java

## Goal:
Build a simple Camunda Client App for Camunda 8, that gets a random picture of a cat, a dog,
or a bear. A list of free APIs can be found here: https://github.com/public-apis/public-apis. We
suggest:

● Cats: https://placekitten.com/  
● Dogs: https://place.dog/  
● Bears: https://placebear.com/

Main Tasks
1. Create a process model (BPMN) that reflects the following
   - Based on a user’s selection the process fetches a picture of either a cat, a dog or
  a bear
2. Build a client app that
   - Deploys the process model to a Camunda installation :white_check_mark:
   - Has a custom REST API to
     - Start a process instance of the Camunda Process :white_check_mark:
     - Retrieve the picture :white_check_mark:
    - Implements a Job worker that
      - Fetches the picture from an API :white_check_mark:
      - Stores the picture in a DB of your choice :white_check_mark:
    - Add automated tests :x:
3. Containerize your application :x:
4. Add a setup to make the app run locally on every machine :x:
   - Think of providing a way to configure the access to different Camunda clusters so
that your app is runnable from anywhere with a provided configuration
5. Add README that includes
   - All relevant documentation about your app :white_check_mark:
   - A how-to part on how to run the app locally :white_check_mark:
   - A small architectural diagram that shows how these components interact with
each other

Bonus points: 

  ● Add a HELM chart for a Kubernetes Deployment :x:  
  ● Simple UI to request and then show the picture (no fancy CSS needed) :x:

## Experience:
Getting the chance to attempt this coding challenge was an enjoyable challenge for myself.  I have had previous experience with .NET Core Job Handlers and Zeebe Client integrations in 2022/2023, and wanted to try out building out a full Camunda Service Application in Java, which I have much less experience in.

There were many learning curves, mostly centralized around the refamiliarizing of Java and the integrations with Camunda/Zeebe.

Of the coding challenge requirements, there were many steps that I was unable to accomplish:  

:x: __TODO__:
-  __Containerize the application:__
   - Unfortunately, when it came to deploying the custom applications, there were difficulties getting the jars published to a functional docker image.
   - This would then include the proper application configurations, providing secrets to the appropriate sources for 3 environments: local, local-docker, and Camunda SaaS
 - __Integration with Camunda SaaS:__
   - It would have been ideal to have a configuration set up to have the Camunda Services publish to a SaaS instance of Camunda, but was more focused on the engine, and containerizing my application than deploying to another environment of Camunda
 - __Testing:__
   - It is my goal to at least at unit tests in the upcoming day, to continue building experience and familiarity in Java
 - __Kubernetes:__
   - No chance to get to attempt a helm chart for the application.
 - __UI:__
   - There were other priorities outside of getting a UI functional, without the backend meeting all criteria  

The coding challenge calls for about 6 hours of work, and challenging myself with a new tech stack, ended up spending about 15-20 hours on the challenge.  With still much left to do, it was much slower development than I would have hoped.

## Running the applicaiton locally:

#### Local Self Contained Camunda instance:
1) "cd Docker/Camunda_sc/"
2) "docker compose up -d"

#### Local Camunda Application Services
1) Requires that the Camunda Self Contained docker images be running
2) PLEASE NOTE, THE DEPLOYMENT TO DOCKER DOES NOT WORK AT THIS TIME:
   - Future state would be:
     - "cd Docker/"
     - "./build.sh"
     - "./deploy-local.sh"
     - Hit the "/camunda/start" POST end point on the Camunda Web Service (documentation below)
     - With the returned processInstanceKey, hit the GET "/camunda/image/{processInstanceKey}" endpoint to retrieve the image
3) CURRENT STATE:
   - Run "com.example.Main" from the Image Job Handler application
   - Run "com.example.camundaservice.CamundaServiceApplication" from the Camunda Service Application


## Repository:
### [Application/CamundaService](https://github.com/mertz1/Camunda_Java/tree/main/Application/CamundaService)
  - Location for the Camunda Spring Service
  - This application publishes the [Camunda Workflow Instance](https://github.com/mertz1/Camunda_Java/blob/main/Application/CamundaService/src/main/resources/ImageProcess.bpmn) on start up
  - Creates two REST End Points:
      - POST "/camunda/start"
        - Body:
           ```
           {
            "imageType": "cat"
           }
           ```
        - Response: ProcessInstanceKey : long
        - About: Kicks off a new instance of the image process, with the parameter "imageType" of "cat", "bear", or "dog" and returns the processInstanceKey of the workflow instance   
      - GET "/camunda/image/{processInstanceKey}"
        - Response: Image (Content-Type: "/image/jpeg")
        - About: Given the processInstanceKey of the REST Api above, this will interogate the database to retrieve the randomly selected image, based on the processInstanceKey
       
    
### [Docker](https://github.com/mertz1/Camunda_Java/tree/main/Docker)
  - This directory contains the docker files used for deploying a locally running instance
  - "Camunda_sc/"
    - Directory containing files needed to run a Self Contained instance of Camunda
  - "postgres/"
    -  Directory containing docker deployment files for the application database required for the "Camunda Service" and "Image Job Worker" to store images.  "init.sql" is used to start up the table schema required. 
  -  "build.sh"
    - A centralized "build.sh" command to kick off the build script for all environment applications.  Kicks off each local build.sh script for Camunda Service / Camunda Spring App and Image Job Worker.  Publishes the docker images needed for each application
   - "deploy-local.sh"
     - A script to run the docker compose for the local environment, based on the published docker images from "build.sh"
    
### [Job Worker](https://github.com/mertz1/Camunda_Java/tree/main/Job%20Worker)
  - This directory contains the separated job worker needed for the "getImage" service task in the camunda workflow
  - This application reads the "imageUrl" variable, to retrieve an image, and store a byte[] into the postgreSQL database
  - This application was split off from the Camunda Service Application to separate dependencies of the REST Apis and underlying job tasks that needed to be ran
