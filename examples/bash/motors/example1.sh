#!/bin/bash

echo 50 > /sys/class/tacho-motor/motor2/duty_cycle_sp
echo run-forever > /sys/class/tacho-motor/motor2/command
echo 50 > /sys/class/tacho-motor/motor4/duty_cycle_sp
echo run-forever > /sys/class/tacho-motor/motor4/command

sleep 5

echo stop > /sys/class/tacho-motor/motor2/command
echo stop > /sys/class/tacho-motor/motor4/command