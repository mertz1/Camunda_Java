#! /bin/bash

# Deploy local docker
echo "-------------! Docker Compose Up - Camunda Self Managed !-------------"
cd Docker/Camunda_sc
docker compose up -d
cd ../../

echo "-------------! Docker Compose Up - COMPLETE: Camunda Self Managed !-------------"

echo "-------------! Docker Compose Up - Application !-------------"
docker compose up -d

echo "-------------! Docker Compose Up - COMPLETE: Application !-------------"

echo "-------------! Docker Compose Up - Database !-------------"

cd Docker/postgres/
docker compose up -d

echo "-------------! Docker Compose Up - COMPLETE: Database !-------------"