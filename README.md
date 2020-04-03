# Point of this project
This codebase was for my groups MTE 380 project. Due to the COVID-19 outbreak, the main controls part of the lab, written in Java, was never finished.

The code was ran on a Raspberry Pi 4, with each sensor libary living in the src/c folder, and compiled on the Raspberry Pi itself. JNI hooks were added to the libaries to allow Java access to the sensor input.

A BNO055 imu was used as our gyro, to keep track of the robots rotation, and the VL53l0/VL53l1 to keep track of its distance away from the walls from the course.

# How to connect to Pi

1. Make sure PI and Computer Ethernet cables are unpluged
2. Search in Windows bar "network connections"
3. Right click on Wifi -> Properties
4. Go to Sharing Tab
5. Enable both checkboxes. Make sure home connection network is Ethernet. Click OK.
6. Plug in ethernet cable into computer.
7. Plug in ethernet cable in pi.
8. Open a terminal. Try to ping pi.local. If it works, congradulations, you can now connect to the pi!
