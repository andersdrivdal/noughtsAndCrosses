package com.example.noughtsandcrosses

import com.example.noughtsandcrosses.api.data.Game

class Holders {

    class CurrentStatusHolder {
        companion object {
            var currentStatus: Boolean = false
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