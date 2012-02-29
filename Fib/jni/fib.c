#include "com_turkcell_fib_FibLib.h"

JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
	return JNI_VERSION_1_6;
}

jlong fib(jlong n) {
	if (n == 0)
		return 0;
	if (n == 1)
		return 1;
	return fib(n - 1) + fib(n - 2);
}

JNIEXPORT jlong JNICALL Java_com_turkcell_fib_FibLib_fibN(JNIEnv *env,
		jclass clazz, jlong n) {
	return fib(n);
}

JNIEXPORT jlong JNICALL Java_com_turkcell_fib_FibLib_fibNI(JNIEnv *env, jclass clazz,
		jlong n) {
	jlong previous = -1;
	jlong result = 1;
	jlong i = 0;
	for (; i <= n; i++) {
		jlong sum = result + previous;
		previous = result;
		result = sum;
	}
	return result;
}

JNIEXPORT jstring JNICALL Java_com_turkcell_fib_FibLib_about(JNIEnv *env, jclass clazz) {
    return (*env)->NewStringUTF(env, "Fibonacci");
}
