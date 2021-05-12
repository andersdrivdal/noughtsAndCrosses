package com.example.noughtsandcrosses

import com.example.noughtsandcrosses.api.data.Game

class Holders {

    class GameFinishedHolder {
        companion object {
            var finished: Boolean = false
        }
    }

    class GameSessionHolder {

        companion object {
            var GameSession: Game? = null
        }
    }

    class PlayerPieceHolder{
        companion object{
            var YourPlayerPiece: String? = null
            var OpponentsPlayerPiece: String? = null
        }
    }


}