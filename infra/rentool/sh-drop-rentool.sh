#!/bin/bash

make drop-kong
sleep 5
make drop-tool-service
sleep 2
make drop-tool-db
sleep 2
make drop-minio
sleep 2
make drop-keycloak
sleep 2
make drop-keycloak-db
sleep 2
make drop-user-service
sleep 2
make drop-user-db
sleep 2
make drop-rent-service
sleep 2
make drop-rent-db
docker rm migrations-tool
docker rm migrations-rent
docker rm migrations-user
