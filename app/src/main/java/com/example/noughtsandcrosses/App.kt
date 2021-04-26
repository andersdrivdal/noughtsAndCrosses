package com.example.noughtsandcrosses

import android.app.Application

class App: Application() {

    companion object{
        lateinit var context: App private set //What is the purpose of this?
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}