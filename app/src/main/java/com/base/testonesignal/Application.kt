package com.base.testonesignal

import android.app.Application
import com.onesignal.OneSignal


class Application: Application() {
    val ONESIGNAL_APP_ID = "65c0aeb5-54e1-4d69-b8ca-0a6e470ead4c"


    override fun onCreate() {
        super.onCreate()

        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

    }

}