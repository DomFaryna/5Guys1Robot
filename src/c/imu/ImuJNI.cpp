#include <jni.h>
#include <ImuJNI.h>
#include <MPU9250_Master_I2C.h>
#include <wiringPi.h>
#include <vector>
#include <iostream>

//DOMDOMDOM remove const
static MPUIMU::Gscale_t GSCALE     = MPUIMU::GFS_500DPS;
static MPUIMU::Ascale_t ASCALE     = MPUIMU::AFS_2G;
static MPU9250::Mscale_t MSCALE    = MPU9250::MFS_16BITS;
static MPU9250::Mmode_t MMODE      = MPU9250::M_100Hz;
static uint8_t SAMPLE_RATE_DIVISOR = 0x09;

// Pin definitions
static const uint8_t intPin = 0;   //  MPU9250 interrupt

// Interrupt support 
//static bool gotNewData;
//static void myinthandler()
//{
//    gotNewData = true;
//}

// Instantiate MPU9250 class in master mode
static MPU9250_Master_I2C imu(ASCALE, GSCALE, MSCALE, MMODE, SAMPLE_RATE_DIVISOR);

JNIEXPORT void JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_calibrate(JNIEnv *env, jobject obj){
    std::cout << "YAAAAAA\n";
    wiringPiSetup();
    switch(imu.begin()){
        case MPUIMU::ERROR_IMU_ID:
            printf("Bad IMU device ID\n");
            break;
        case MPUIMU::ERROR_MAG_ID:
            printf("Bad magnetometer device ID\n");
            break;
        case MPUIMU::ERROR_SELFTEST:
            printf("Failed SelfTest");
            break;
        default:
            break;
    }

}

JNIEXPORT jdoubleArray JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_getRawGyro(JNIEnv *env, jobject obj){
    static float x, y, z;
    std::vector<double> he(3);
    imu.readGyrometer(x, y, z);
    he[0] = x;
    he[1] = y;
    he[2] = z;
    jdoubleArray sol = env->NewDoubleArray(3);
    env->SetDoubleArrayRegion(sol, 0, he.size(), &he[0]);
    return sol;
}

JNIEXPORT jdoubleArray JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_getRawAcc(JNIEnv *env, jobject obj){
    static float x, y, z;
    std::vector<double> he(3);
    imu.readAccelerometer(x, y, z);
    he[0] = x;
    he[1] = y;
    he[2] = z;
    jdoubleArray sol = env->NewDoubleArray(3);
    env->SetDoubleArrayRegion(sol, 0, he.size(), &he[0]);
    return sol;
}

JNIEXPORT jdoubleArray JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_ImuJNI_getRawMag(JNIEnv *env, jobject obj){
    static float x, y, z;
    std::vector<double> he(3);
    imu.readMagnetometer(x, y, z);
    he[0] = x;
    he[1] = y;
    he[2] = z;
    jdoubleArray sol = env->NewDoubleArray(3);
    env->SetDoubleArrayRegion(sol, 0, he.size(), &he[0]);
    return sol;}


