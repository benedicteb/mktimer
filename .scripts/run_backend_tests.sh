#!/bin/sh
cd mktimer-backend

docker-compose up -d

mvn test -B
