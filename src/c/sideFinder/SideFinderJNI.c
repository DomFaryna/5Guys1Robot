// JESUS THATS A LOT OF IMPORTS
#include <jni.h>
#include "SideFinderJNI.h"
#include <malloc.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <linux/i2c-dev.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/time.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <signal.h>
#include <stdint.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <pthread.h>
#include <stdio.h>
#include <time.h>

#include "VL53L1X_api.h"
#include "VL53L1X_calibration.h"
#include "vl53l1_platform.h"

int bus = 1;
int address = 0x29;
// Have no idea what DEV is, but it is needed for initilizing, for some reason
int Dev = 0;


JNIEXPORT void JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_SideFinderJNI_init(JNIEnv *env, jobject obj){
    uint8_t byteData, sensorState = 0;
    uint16_t wordData;

    int file = VL53L1X_UltraLite_Linux_I2C_Init(Dev, bus, address);
    if (file == -1){
        printf("Something has gone terrible wrong trying to initilized the sideFinder");
        return;
    }
    VL53L1X_SensorInit(Dev);
    VL53L1X_SetDistanceMode(Dev, 2); /* 1=short, 2=long */
    VL53L1X_SetTimingBudgetInMs(Dev, 100);
    VL53L1X_SetInterMeasurementInMs(Dev, 100);
    VL53L1X_StartRanging(Dev);

}

JNIEXPORT jint JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_SideFinderJNI_getDistance(JNIEnv *env, jobject obj){
    static VL53L1X_Result_t results;
    VL53L1X_GetResult(Dev, &results);
    return results.Distance;
}

