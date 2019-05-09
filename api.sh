#!/bin/bash
set -eu
cd $(dirname "$0")

docker run --network host --user $(id -u):$(id -g) --rm -v $PWD:/pwd \
    swaggerapi/swagger-codegen-cli generate \
    -i http://localhost:8080/v2/api-docs \
    -l typescript-fetch \
    -o /pwd/src/api

rm -vr src/api/.[!.]* src/api/*.sh