#!/usr/bin/env bash

curl -v -H 'Content-Type: application/json' "$1" -d "$2"
