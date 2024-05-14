#! /bin/bash

echo "-------------! Image Job Worker - Maven Package !-------------"
mvn package

echo "-------------! Image Job Worker - COMPLETE: Packaging Complete !-------------"

echo "-------------! Image Job Worker - Build docker image !-------------"

docker image build -t image-job-worker:latest .

echo "-------------! Image Job Worker - COMPLETE: Docker Image Built !-------------"