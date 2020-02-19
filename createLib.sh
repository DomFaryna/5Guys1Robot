#!/bin/bash
# to get the proper h file, run `javac -h . SomeFile.java`. Make sure the function headers match the ones found in the h file.
# takes a c file and converts it to a proper libary
dir=$1
file=$2
gcc -o build/libs/lib${file}.so -fPIC -lc -shared -I ${JAVA_HOME}/include -I ${JAVA_HOME}/include/linux $dir


