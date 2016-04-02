/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

#ifndef _NET_JAVA_SIP_COMMUNICATOR_PLUGIN_ADDRBOOK_MSOUTLOOK_MAPINOTIFICATION_H_
#define _NET_JAVA_SIP_COMMUNICATOR_PLUGIN_ADDRBOOK_MSOUTLOOK_MAPINOTIFICATION_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <jni.h>
#include <mapidefs.h>
#include <mapix.h>

/**
 * Manages notification for the message data base (used to get the list of
 * contact).
 *
 * @author Vincent Lucas
 */

boolean MAPINotification_callCallbackMethod(LPSTR iUnknown, long objectAddr);

void MAPINotification_jniCallDeletedMethod(LPSTR iUnknown);
void MAPINotification_jniCallInsertedMethod(LPSTR iUnknown);
void MAPINotification_jniCallUpdatedMethod(LPSTR iUnknown);

void MAPINotification_jniCallCalendarDeletedMethod(LPSTR iUnknown);
void MAPINotification_jniCallCalendarInsertedMethod(LPSTR iUnknown);
void MAPINotification_jniCallCalendarUpdatedMethod(LPSTR iUnknown);

LONG
STDAPICALLTYPE MAPINotification_onNotify
    (LPVOID lpvContext, ULONG cNotifications, LPNOTIFICATION lpNotifications,
    ULONG type);

void
MAPINotification_registerJniNotificationsDelegate
    (JNIEnv *jniEnv, jobject notificationsDelegate);

void
MAPINotification_registerCalendarJniNotificationsDelegate
    (JNIEnv *jniEnv, jobject notificationsDelegate);
void
MAPINotification_registerNativeNotificationsDelegate
    (void * deletedMethod, void * insertedMethod, void *updatedMethod);

void
MAPINotification_registerCalendarNativeNotificationsDelegate
    (void * deletedMethod, void * insertedMethod, void *updatedMethod);

void MAPINotification_registerNotifyAllMsgStores(LPMAPISESSION mapiSession);

void MAPINotification_unregisterJniNotificationsDelegate(JNIEnv *jniEnv);
void MAPINotification_unregisterNativeNotificationsDelegate();
void MAPINotification_unregisterNotifyAllMsgStores(void);

void MAPINotification_unregisterJniCalendarNotificationsDelegate(JNIEnv *jniEnv);
void MAPINotification_unregisterNativeCalendarNotificationsDelegate();


#ifdef __cplusplus
}
#endif

#endif /* #ifndef _NET_JAVA_SIP_COMMUNICATOR_PLUGIN_ADDRBOOK_MSOUTLOOK_MAPINOTIFICATION_H_ */
