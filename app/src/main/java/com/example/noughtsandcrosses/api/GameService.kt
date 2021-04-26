package com.example.noughtsandcrosses.api

import android.app.Application
import com.example.noughtsandcrosses.App
import com.example.noughtsandcrosses.api.GameService.context
import com.example.noughtsandcrosses.api.data.Game


typealias GameServiceCallback = (state: Game?, errorCode:Int? ) -> Unit

object GameService {

    private val context = App.context

}