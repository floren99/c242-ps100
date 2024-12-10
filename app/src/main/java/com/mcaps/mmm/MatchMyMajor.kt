package com.mcaps.mmm

import android.app.Application
import android.util.Log
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.google.firebase.FirebaseApp

@GlideModule
class MatchMyMajor : AppGlideModule() {
    /*
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Log.d("Application", "FirebaseApp Initialized")
    }*/
}