#!/bin/bash

docker-compose up -d
echo "Waiting for the database to turn on..."
sleep 15s
./gradlew bootRun