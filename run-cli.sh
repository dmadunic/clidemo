#!/bin/bash
java -Dspring.profiles.active=${1:-dev} -jar build/libs/clidemo-0.2.0.jar
