#!/bin/bash

echo 50 > /sys/class/tacho-motor/motor2/duty_cycle_sp
echo run-forever > /sys/class/tacho-motor/motor2/command
echo 50 > /sys/class/tacho-motor/motor4/duty_cycle_sp
echo run-forever > /sys/class/tacho-motor/motor4/command

sleep 5

echo stop > /sys/class/tacho-motor/motor2/command
echo stop > /sys/class/tacho-motor/motor4/command

echo 50 > duty_cycle_sp
echo run-forever > command
sleep 5
echo stop > command

echo 50 > speed_sp
cat speed_sp

cat position_sp
echo 100 > position_sp
echo run-to-abs-pos > command

echo 180 > position
echo run-to-rel-pos > command
cat speed_regulation

echo on > speed_regulation