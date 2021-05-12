package com.example.noughtsandcrosses

import android.content.Intent
import com.example.noughtsandcrosses.GameManager.StartingGameState
import com.example.noughtsandcrosses.GameManager.updateGame
import com.example.noughtsandcrosses.api.GameService
import com.example.noughtsandcrosses.api.data.Game
import com.example.noughtsandcrosses.api.data.GameState

object GameManager {

    var player: String? = null
    var mark: String? = null
    var state: GameState? = null


    var StartingGameState: GameState = mutableListOf(
        mutableListOf("0", "0","0"),
        mutableListOf("0", "0","0"),
        mutableListOf("0", "0","0")
    )

    fun createGame(player: String) {

        GameService.createGame(player, StartingGameState) { game: Game?, err: Int? ->
            if (err != null) {
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                /// TODO("We have a game. What to do?)
                Holders.GameSessionHolder.GameSession = game
                val intent = Intent(MainActivity.mainContext, GameActivity::class.java)
                MainActivity.mainContext.startActivity(intent)
            }
        }

    }

    fun joinGame(player: String, gameId: String) {

        GameService.joinGame(player, gameId) { game: Game?, err: Int? ->
            if (err != null) {
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                /// TODO("We have a game. What to do?)
                Holders.GameSessionHolder.GameSession = game
                val intent = Intent(MainActivity.mainContext, GameActivity::class.java)
                MainActivity.mainContext.startActivity(intent)
            }
        }
    }

    fun updateGame(gameId: String, state: GameState) {

        GameService.updateGame(gameId, state) { game: Game?, err: Int? ->
            if (err != null) {
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                /// TODO("We have a game. What to do?)
                Holders.GameSessionHolder.GameSession = game
            }
        }

    }

    fun pollGame(gameId: String) {

        GameService.pollGame(gameId) { game: Game?, err: Int? ->
            if (err != null) {
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                /// TODO("We have a game. What to do?)
                Holders.GameSessionHolder.GameSession = game
            }
        }
    }


    fun makeMove(position: Position){
        val temporaryState : GameState = Holders.GameSessionHolder.GameSession!!.state
        if (temporaryState[position.row][position.column] == "0") {
            temporaryState[position.row][position.column] = Holders.PlayerPieceHolder.YourPlayerPiece!!
            updateGame(Holders.GameSessionHolder.GameSession!!.gameId, temporaryState)
            }
        }

    fun checkGameStatus(Mark: String,state: GameState): ThreeInARow? {
        if (state[0][0] == Mark && state[0][1] == Mark && state[0][2] == Mark) {
            return ThreeInARow.ROW_0
        } else if (state[1][0] == Mark && state[1][1] == Mark && state[1][2] == Mark) {
            return ThreeInARow.ROW_1
        } else if (state[2][0] == Mark && state[2][1] == Mark && state[2][2] == Mark) {
            return ThreeInARow.ROW_2
        } else if (state[0][0] == Mark && state[1][0] == Mark && state[2][0] == Mark) {
            return ThreeInARow.COLUMN_0
        } else if (state[0][1] == Mark && state[1][1] == Mark && state[2][1] == Mark) {
            return ThreeInARow.COLUMN_1
        } else if (state[0][2] == Mark && state[1][2] == Mark && state[2][2] == Mark) {
            return ThreeInARow.COLUMN_2
        } else if (state[0][0] == Mark && state[1][1] == Mark && state[2][2] == Mark) {
            return ThreeInARow.DIAGONAL_LEFT
        } else if (state[0][2] == Mark && state[1][1] == Mark && state[2][0] == Mark) {
            return ThreeInARow.DIAGONAL_RIGHT
        }
        return null
    }

}




