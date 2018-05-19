#!/bin/sh

while :; do
  sleep 0.500;
  curl http://helloswarm-myproject.192.168.64.8.nip.io/hello
  echo "";
done