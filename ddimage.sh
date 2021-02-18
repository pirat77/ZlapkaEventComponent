#!/bin/bash
mvn package
docker build -t pirat0x77/zlapka-event-component-0.1:latest .
docker push pirat0x77/zlapka-event-component-0.1:latest
kubectl apply -f event-deploy.yaml
kubectl apply -f event-service.yaml
