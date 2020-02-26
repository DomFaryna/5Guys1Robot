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

Adafruit_BNO055 bno = Adafruit_BNO055();
bool init = false;

JNIEXPORT void JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_calibrate(JNIEnv *env, jobject obj){
    if(gpioInitalise() < 0){
        std::cout << "Init error of GPIO \n";
        return ;
    }
    bno._HandleBNO=i2cOpen(3, BNO055_ADDRESS_A, 0);
    if(!bno.begin()){
        std::cout << "Could not detect BNO. Fix it\n";
        return;
    }
    init = true;
    bno.setExtCrystalUse(true);

}

JNIEXPORT jdoubleArray JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_getRawGyro(JNIEnv *env, jobject obj){
    if(!init){
        return env->NewDoubleArray(3);
    } 
    imu::Vector<3> euler = bno.getVector(Adafruit_BNO055::VECTOR_EULER);
    std::vector<double> he(3);
    he[0] = euler.x();
    he[1] = euler.y();
    he[2] = euler.z();
    jdouble sol = env->NewDoubleArray(3);
    env.SetDoubleArrayRegion(sol, 0, he.size(), &he[0]);
    return sol;
}

// TODO implement
JNIEXPORT jdoubleArray JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_getRawAcc(JNIEnv *env, jobject obj){
    std::vector<double> he(3);
    jdouble sol = env->NewDoubleArray(3);
    env.SetDoubleArrayRegion(sol, 0, he.size(), &he[0]);
    return sol;
}

// TODO implement
JNIEXPORT jdoubleArray JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_getRawMag(JNIEnv *env, jobject obj){
    std::vector<double> he(3);
    jdouble sol = env->NewDoubleArray(3);
    env.SetDoubleArrayRegion(sol, 0, he.size(), &he[0]);
    return sol;}
