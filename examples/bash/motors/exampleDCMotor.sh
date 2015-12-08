#!/bin/bash

echo 50 > /sys/class/dc-motor/motor0/duty_cycle_sp
echo run-forever > /sys/class/dc-motor/motor0/command

sleep 5

echo stop > /sys/class/dc-motor/motor0/command
echo stop > command
