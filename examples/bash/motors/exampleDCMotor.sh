#!/bin/bash

echo 50 > /sys/class/dc-motor/motor0/duty_cycle_sp
echo run-forever > /sys/class/dc-motor/motor0/command

sleep 5

echo stop > /sys/class/dc-motor/motor0/command
echo stop > command

echo dc-motor > /sys/class/lego-port/port4/mode

echo 50 > duty_cycle_sp
echo run-forever > command
sleep 1
echo stop > command

echo 50 > /sys/class/dc-motor/motor8/duty_cycle_sp
