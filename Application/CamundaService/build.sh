#! /bin/bash
echo "-------------! Camunda Spring App - Maven Package !-------------"
mvn package
echo "-------------! Camunda Spring App - COMPLETE: Maven Package Built !-------------"

echo "-------------! Camunda Spring App - Build Docker Image !-------------"

docker image build -t camunda-spring-app:latest .

echo "-------------! Camunda Spring App - COMPLETE: Build Docker Image !-------------"
