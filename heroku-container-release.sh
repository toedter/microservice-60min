#!/bin/bash
imageId= docker inspect registry.heroku.com/microservice-60min/web --format={{.Id}}
curl -n -X PATCH https://api.heroku.com/apps/microservice-60min/formation \
-d '{"updates":[{"type":"web","docker_image":"'"$imageId"'"}]}' \
-H "Content-Type: application/json" \
-H "Accept: application/vnd.heroku+json; version=3.docker-releases" \
-H "Authorization: Bearer $HEROKU_AUTH_TOKEN"