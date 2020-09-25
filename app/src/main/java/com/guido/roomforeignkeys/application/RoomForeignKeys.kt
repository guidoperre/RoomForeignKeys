package com.guido.roomforeignkeys.application

import android.app.Application
import com.facebook.stetho.Stetho

class RoomForeignKeys: Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

}