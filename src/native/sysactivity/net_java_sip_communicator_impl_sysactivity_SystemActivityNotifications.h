/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications */

#ifndef _Included_net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications
#define _Included_net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications
#ifdef __cplusplus
extern "C" {
#endif
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_DISPLAY_SLEEP
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_DISPLAY_SLEEP 2L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_DISPLAY_WAKE
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_DISPLAY_WAKE 3L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_DNS_CHANGE
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_DNS_CHANGE 10L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_ENDSESSION
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_ENDSESSION 12L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_NETWORK_CHANGE
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_NETWORK_CHANGE 9L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_QUERY_ENDSESSION
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_QUERY_ENDSESSION 11L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREEN_LOCKED
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREEN_LOCKED 7L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREEN_UNLOCKED
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREEN_UNLOCKED 8L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREENSAVER_START
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREENSAVER_START 4L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREENSAVER_STOP
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREENSAVER_STOP 6L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREENSAVER_WILL_STOP
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SCREENSAVER_WILL_STOP 5L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SLEEP
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_SLEEP 0L
#undef net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_WAKE
#define net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_NOTIFY_WAKE 1L
/*
 * Class:     net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications
 * Method:    allocAndInit
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_allocAndInit
  (JNIEnv *, jclass);

/*
 * Class:     net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications
 * Method:    getLastInput
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_getLastInput
  (JNIEnv *, jclass);

/*
 * Class:     net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications
 * Method:    release
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_release
  (JNIEnv *, jclass, jlong);

/*
 * Class:     net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications
 * Method:    setDelegate
 * Signature: (JLnet/java/sip/communicator/impl/sysactivity/SystemActivityNotifications/NotificationsDelegate;)V
 */
JNIEXPORT void JNICALL Java_net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_setDelegate
  (JNIEnv *, jclass, jlong, jobject);

/*
 * Class:     net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications
 * Method:    start
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_start
  (JNIEnv *, jclass, jlong);

/*
 * Class:     net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications
 * Method:    stop
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_net_java_sip_communicator_impl_sysactivity_SystemActivityNotifications_stop
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif
