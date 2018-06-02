#!/bin/bash
curl -n -X PATCH https://api.heroku.com/apps/microservice-60min/formation \
-d '{"updates":[{"type":"web","docker_image":"54b2fcf91c549dfa4da3a3ec268bf896eb0d22bea8899c94c50201cc50052af7"}]}' \
-H "Content-Type: application/json" \
-H "Accept: application/vnd.heroku+json; version=3.docker-releases" \
-H "Authorization: Bearer eb305d9e-75ff-4881-acbc-325c28efb3ae"