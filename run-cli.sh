#!/bin/bash
java -Dspring.profiles.active=${1:-dev} -jar build/libs/clidemo-0.1.2.jar
