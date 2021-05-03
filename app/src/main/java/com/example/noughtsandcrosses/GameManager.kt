package com.example.noughtsandcrosses

import android.os.SystemClock
import com.example.noughtsandcrosses.api.GameService
import com.example.noughtsandcrosses.api.GameServiceCallback
import com.example.noughtsandcrosses.api.data.Game
import com.example.noughtsandcrosses.api.data.GameState

object GameManager {

    var player:String? = null
    var state:GameState? = null

    val StartingGameState:GameState = listOf(listOf(0,0,0),listOf(0,0,0),listOf(0,0,0))
    val NewGameState:GameState = listOf(listOf(0,1,0),listOf(0,1,0),listOf(0,1,0))

    fun createGame(player:String){

        GameService.createGame(player,StartingGameState) { game: Game?, err: Int? ->
            if(err != null){
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                /// TODO("We have a game. What to do?)

                //To verify functional GameService
                println("111111111111111${game}")
                joinGame("Panders", game!!.gameId)
                updateGame(game!!.gameId, NewGameState)
                println("222222222222222${game} ")
                pollGame(game.gameId)
                println("333333333333${game}")

            }
        }

    }

    fun joinGame(player:String, gameId:String){

        GameService.joinGame(player,gameId) { game: Game?, err: Int? ->
            if(err != null){
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                /// TODO("We have a game. What to do?)
            }
        }
    }

    fun updateGame(gameId: String, state: GameState){

        GameService.updateGame(gameId, state) { game: Game?, err: Int? ->
            if(err != null){
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                /// TODO("We have a game. What to do?)
            }
        }

    }

    fun pollGame(gameId: String){

        GameService.pollGame(gameId) { game: Game?, err: Int? ->
            if(err != null){
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                /// TODO("We have a game. What to do?)

            }
        }

    }

}