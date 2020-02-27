// JNI imports
#include <jni.h>
#include "ImuJNI.h"
#include <vector>

// BNO imports
#include <pigpio.h>
#include <iostream>
#include "RPi_Sensor.h"
#include "RPi_BNO055.h"
#include <imumaths.h>

Adafruit_BNO055 myBno = Adafruit_BNO055();
bool init = false;

JNIEXPORT void JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_calibrate(JNIEnv *env, jobject obj){
    if(gpioInitialise() < 0){
        std::cout << "Init error of GPIO \n";
        return ;
    }
    myBno._HandleBNO=i2cOpen(3, BNO055_ADDRESS_A, 0);
    if(!myBno.begin()){
        std::cout << "Could not detect BNO. Fix it\n";
        return;
    }
    init = true;
    myBno.setExtCrystalUse(true);
}

JNIEXPORT jdoubleArray JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_getRawGyro(JNIEnv *env, jobject obj){
    if(!init){
        return env->NewDoubleArray(3);
    } 
    imu::Vector<3> euler = myBno.getVector(Adafruit_BNO055::VECTOR_EULER);
    std::vector<double> he(3);
    he[0] = euler.x();
    he[1] = euler.y();
    he[2] = euler.z();
    jdoubleArray sol = env->NewDoubleArray(3);
    env->SetDoubleArrayRegion(sol, 0, he.size(), &he[0]);
    return sol;
}

// TODO implement
JNIEXPORT jdoubleArray JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_getRawAcc(JNIEnv *env, jobject obj){
    if(!init){
        return env->NewDoubleArray(3);
    } 
    imu::Vector<3> euler = myBno.getVector(Adafruit_BNO055::VECTOR_ACCELEROMETER);
    std::vector<double> he(3);
    he[0] = euler.x();
    he[1] = euler.y();
    he[2] = euler.z();
    jdoubleArray sol = env->NewDoubleArray(3);
    env->SetDoubleArrayRegion(sol, 0, he.size(), &he[0]);
    return sol;
}

// TODO implement
JNIEXPORT jdoubleArray JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_getRawMag(JNIEnv *env, jobject obj){
    std::vector<double> he(3);
    jdoubleArray sol = env->NewDoubleArray(3);
    env->SetDoubleArrayRegion(sol, 0, he.size(), &he[0]);
    return sol;}
