#!/usr/bin/bash

counter=1
while [ 1 ]
do
  echo "Request # ${counter}"
  curl -f http://localhost:8080/api/v1/check
  if [ $? != 0 ]; then
    break
  fi
  echo
  (( counter += 1 ))
done