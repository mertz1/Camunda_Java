#! /bin/bash

# Build Camunda Web Application
echo "-------------! Building Camunda Spring App !-------------"

cd Application/CamundaService/
./build.sh
cd ../../

echo "-------------! Complete! - Build Camunda Spring App !-------------"
# Build Image Job Worker
echo "-------------! Building Image Job Worker !-------------"

cd "Job Worker/ImageJobWorker/"
./build.sh
cd ../../

echo "-------------! Complete! - Build Image Job Worker !-------------"