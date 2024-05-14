#! /bin/bash

# Deploy local docker
echo "-------------! Docker Compose Up - Application !-------------"
docker compose up -d

echo "-------------! Docker Compose Up - COMPLETE: Application !-------------"

echo "-------------! Docker Compose Up - Database !-------------"

cd Docker/postgres/
docker compose up -d

echo "-------------! Docker Compose Up - COMPLETE: Database !-------------"