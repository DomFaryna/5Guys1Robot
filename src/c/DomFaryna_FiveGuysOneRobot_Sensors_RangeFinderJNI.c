#include <jni.h>
#include <stdio.h>

JNIEXPORT void JNICALL Java_DomFaryna_FiveGuysOneRobot_Sensors_RangeFinderJNI_print
  (JNIEnv *, jobject){
    printf("Hello Buddy\n");
return;
}
