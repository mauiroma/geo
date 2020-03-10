#!/bin/bash

docker build -t cqrs/bank-service:0.0.1 --no-cache . 
docker run -p 8080:8080 cqrs/bank-service:0.0.1
