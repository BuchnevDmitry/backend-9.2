#!/bin/bash

make start-keycloak
sleep 2
make start-tool-service
sleep 2
make start-rent-service
sleep 2
make start-user-service
sleep 2
make start-kong
