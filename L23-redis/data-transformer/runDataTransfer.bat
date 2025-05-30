#!/bin/bash

../../gradlew :L23-redis:data-transformer:build

docker load --input build/jib-image.tar

docker stop data-transformer-1
docker stop data-transformer-2

docker rm data-transformer-1
docker rm data-transformer-2


docker run --rm -d --name data-transformer-1 `
--memory=256m `
--cpus 1 `
--net
-e JAVA_TOOL_OPTIONS="-XX:InitialRAMPercentage=80 -XX:MaxRAMPercentage=80" `
-e CLIENT-NAME="data-transformer-1" `
-p 8081:8081 `
localrun/data-transformer:latest

docker run --rm -d --name data-transformer-2 `
--memory=256m `
--cpus 1 `
-e JAVA_TOOL_OPTIONS="-XX:InitialRAMPercentage=80 -XX:MaxRAMPercentage=80" `
-e CLIENT-NAME="data-transformer-2" `
-p 8082:8082 `
localrun/data-transformer:latest

