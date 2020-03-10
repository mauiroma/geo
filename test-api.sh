#!/bin/bash

while true
do
  curl -v -X GET http://localhost:8081/api/v1/accounts
    curl -X POST -H "Content-Type: application/json" -d '{"name":"John Doe", "email":"john.doe@google.com"}' "http://localhost:8081/api/v1/create-account"
done
