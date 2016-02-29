#!/bin/bash
str=("172.16.117.154:5555" "172.16.117.142:5555")
for s in ${str[@]}; do
   echo "$s"
   nohup sh run.sh $s > ./log/mainlog$s 2>&1 &
   sleep 15s
done
