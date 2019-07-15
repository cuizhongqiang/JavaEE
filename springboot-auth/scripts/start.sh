#!/bin/bash
jarfile=$(ls ../target/*.jar)
echo $jarfile
nohup java -jar $jarfile --server.port=5001 &
