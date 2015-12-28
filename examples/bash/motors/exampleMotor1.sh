#!/bin/bash

echo reset > /sys/class/tacho-motor/motor0/command
echo reset > /sys/class/tacho-motor/motor1/command

echo -500 > /sys/class/tacho-motor/motor0/speed_sp
echo run-forever > /sys/class/tacho-motor/motor0/command
echo -500 > /sys/class/tacho-motor/motor1/speed_sp
echo run-forever > /sys/class/tacho-motor/motor1/command

sleep 1

echo stop > /sys/class/tacho-motor/motor0/command
echo stop > /sys/class/tacho-motor/motor1/command

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

cat position_sp
echo run-to-abs-pos > command
while true; do echo -en "\033[0G$(cat position)   "; done