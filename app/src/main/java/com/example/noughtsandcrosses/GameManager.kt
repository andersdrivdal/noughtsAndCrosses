package com.example.noughtsandcrosses

import android.content.Intent
import com.example.noughtsandcrosses.api.GameService
import com.example.noughtsandcrosses.api.data.Game
import com.example.noughtsandcrosses.api.data.GameState

object GameManager {

    var StartingGameState: GameState = mutableListOf(
        mutableListOf("0", "0","0"),
        mutableListOf("0", "0","0"),
        mutableListOf("0", "0","0")
    )

    fun createGame(player: String) {
        GameService.createGame(player, StartingGameState) { game: Game?, err: Int? ->
            if (err != null) {
                println("$err")
            } else {
                Holders.GameSessionHolder.GameSession = game
                val intent = Intent(MainActivity.mainContext, GameActivity::class.java)
                MainActivity.mainContext.startActivity(intent)
            }
        }

    }

    fun joinGame(player: String, gameId: String) {
        GameService.joinGame(player, gameId) { game: Game?, err: Int? ->
            if (err != null) {
                println("$err")
            } else {
                Holders.GameSessionHolder.GameSession = game
                val intent = Intent(MainActivity.mainContext, GameActivity::class.java)
                MainActivity.mainContext.startActivity(intent)
            }
        }
    }

    fun updateGame(gameId: String, state: GameState) {
        GameService.updateGame(gameId, state) { game: Game?, err: Int? ->
            if (err != null) {
                println("$err")
            } else {
                Holders.GameSessionHolder.GameSession = game
            }
        }

    }

    fun resetGame(){
        updateGame(Holders.GameSessionHolder.GameSession!!.gameId, StartingGameState)
    }

    fun pollGame(gameId: String) {
        GameService.pollGame(gameId) { game: Game?, err: Int? ->
            if (err != null) {
                println("$err")
            } else {
                Holders.GameSessionHolder.GameSession = game
            }
        }
    }

    fun makeMove(position: Position){
        val holdTempState : GameState = Holders.GameSessionHolder.GameSession!!.state
        if (holdTempState[position.row][position.column] == "0") {
            holdTempState[position.row][position.column] = Holders.PlayerPieceHolder.YourPlayerPiece!!
            updateGame(Holders.GameSessionHolder.GameSession!!.gameId, holdTempState)
            }
        }

    fun checkGameStatus(Mark: String,state: GameState): WinSequence? {
        if (state[0][0] == Mark && state[0][1] == Mark && state[0][2] == Mark) {
            return WinSequence.TOP_ACROSS
        } else if (state[1][0] == Mark && state[1][1] == Mark && state[1][2] == Mark) {
            return WinSequence.MID_ACROSS
        } else if (state[2][0] == Mark && state[2][1] == Mark && state[2][2] == Mark) {
            return WinSequence.LOW_ACROSS
        } else if (state[0][0] == Mark && state[1][0] == Mark && state[2][0] == Mark) {
            return WinSequence.LEFT_DOWN
        } else if (state[0][1] == Mark && state[1][1] == Mark && state[2][1] == Mark) {
            return WinSequence.MID_DOWN
        } else if (state[0][2] == Mark && state[1][2] == Mark && state[2][2] == Mark) {
            return WinSequence.RIGHT_DOWN
        } else if (state[0][0] == Mark && state[1][1] == Mark && state[2][2] == Mark) {
            return WinSequence.DIAGONAL_LEFT_TO_RIGHT
        } else if (state[0][2] == Mark && state[1][1] == Mark && state[2][0] == Mark) {
            return WinSequence.DIAGONAL_RIGHT_TO_LEFT
        }
        return null
    }

}




