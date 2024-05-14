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
a. Based on a user’s selection the process fetches a picture of either a cat, a dog or
a bear
2. Build a client app that
a. Deploys the process model to a Camunda installation
b. Has a custom REST API to
i. Start a process instance of the Camunda Process
ii. Retrieve the picture
c. Implements a Job worker that
i. Fetches the picture from an API
ii. Stores the picture in a DB of your choice
d. Add automated tests
3. Containerize your application
4. Add a setup to make the app run locally on every machine
a. Think of providing a way to configure the access to different Camunda clusters so
that your app is runnable from anywhere with a provided configuration

5. Add README that includes
a. All relevant documentation about your app
b. A how-to part on how to run the app locally
c. A small architectural diagram that shows how these components interact with
each other

Bonus points: 
● Add a HELM chart for a Kubernetes Deployment  
● Simple UI to request and then show the picture (no fancy CSS needed)

## Repository:
- [Application/CamundaService](https://github.com/mertz1/Camunda_Java/tree/main/Application/CamundaService)
- [Docker](https://github.com/mertz1/Camunda_Java/tree/main/Docker)
- [Job Worker](https://github.com/mertz1/Camunda_Java/tree/main/Job%20Worker)
- build.sh
- deploy-local.sh
- docker-compose.yaml
- 
