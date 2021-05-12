package com.example.noughtsandcrosses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.noughtsandcrosses.databinding.ActivityGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Holders.GameSessionHolder.GameSession!!.players.size == 1){
            Holders.PlayerPieceHolder.YourPlayerPiece = "X"
            Holders.PlayerPieceHolder.OpponentsPlayerPiece = "O"
        }else{
            Holders.PlayerPieceHolder.YourPlayerPiece = "O"
            Holders.PlayerPieceHolder.OpponentsPlayerPiece = "X"
        }

        Holders.GameFinishedHolder.finished = false

        updateScreen()

        binding.topLeft.setOnClickListener { onPiecePlaced(Position(0, 0)) }
        binding.topMiddle.setOnClickListener { onPiecePlaced(Position(0, 1)) }
        binding.topRight.setOnClickListener { onPiecePlaced(Position(0, 2)) }
        binding.midLeft.setOnClickListener { onPiecePlaced(Position(1, 0)) }
        binding.midMiddle.setOnClickListener { onPiecePlaced(Position(1, 1)) }
        binding.midRight.setOnClickListener { onPiecePlaced(Position(1, 2)) }
        binding.lowLeft.setOnClickListener { onPiecePlaced(Position(2, 0)) }
        binding.lowMiddle.setOnClickListener { onPiecePlaced(Position(2, 1)) }
        binding.lowRight.setOnClickListener { onPiecePlaced(Position(2, 2)) }

        binding.exitGame.setOnClickListener { finish() }

    }

    private fun onPiecePlaced(position: Position) {
        if (move()) {
            GameManager.makeMove(position)
        }
    }

    private fun countCurrentBoard(): Int {
        var count = 0
        for (row in 0..2) {
            for (column in 0..2) {
                if (Holders.GameSessionHolder.GameSession?.state!![row][column] != "0") {
                    count++
                }
            }
        }
        return count
    }

    private fun move(): Boolean {
        var yourMove = false
        val count = countCurrentBoard()
        when (Holders.PlayerPieceHolder.YourPlayerPiece) {
            "X" -> {when (count){0, 2, 4, 6, 8 -> yourMove = true}
            }
            "O" -> {when (count){1, 3, 5, 7 -> yourMove = true}
            }
        }
        return yourMove
    }

    fun updateScreen() {
        CoroutineScope(Dispatchers.IO).launch {
            while (!Holders.GameFinishedHolder.finished) {
                GameManager.pollGame(Holders.GameSessionHolder.GameSession!!.gameId)
                delay(1000)
                this@GameActivity.runOnUiThread {
                    val game = Holders.GameSessionHolder.GameSession!!
                    ("Game ID: " + Holders.GameSessionHolder.GameSession?.gameId).also { binding.GameIdText.text = it }
                    binding.topLeft.text =
                        Holders.GameSessionHolder.GameSession!!.state[0][0].takeUnless { it == "0" }
                    binding.topMiddle.text =
                        Holders.GameSessionHolder.GameSession!!.state[0][1].takeUnless { it == "0" }
                    binding.topRight.text =
                        Holders.GameSessionHolder.GameSession!!.state[0][2].takeUnless { it == "0" }
                    binding.midLeft.text =
                        Holders.GameSessionHolder.GameSession!!.state[1][0].takeUnless { it == "0" }
                    binding.midMiddle.text =
                        Holders.GameSessionHolder.GameSession!!.state[1][1].takeUnless { it == "0" }
                    binding.midRight.text =
                        Holders.GameSessionHolder.GameSession!!.state[1][2].takeUnless { it == "0" }
                    binding.lowLeft.text =
                        Holders.GameSessionHolder.GameSession!!.state[2][0].takeUnless { it == "0" }
                    binding.lowMiddle.text =
                        Holders.GameSessionHolder.GameSession!!.state[2][1].takeUnless { it == "0" }
                    binding.lowRight.text =
                        Holders.GameSessionHolder.GameSession!!.state[2][2].takeUnless { it == "0" }
                    if (Holders.GameSessionHolder.GameSession!!.players.size == 2) {
                        binding.playerOne.text = game.players[0]
                        "VS".also { binding.versus.text = it }
                        binding.playerTwo.text = game.players[1]
                        binding.GameIdText.text = null
                    }
                    gameFinishedTest()
                }
            }
        }
    }


    private fun gameFinishedTest() {
        val game = Holders.GameSessionHolder.GameSession?.state!!
        when (Holders.PlayerPieceHolder.YourPlayerPiece) {
            "X" -> {
                if (GameManager.checkGameStatus("X", game) != null) {
                    GameManager.checkGameStatus("X", game)//?.let { showWinner(it) }
                    binding.GameIdText.text = "Winner!"
                    Holders.GameFinishedHolder.finished = true
                }
                if (GameManager.checkGameStatus("O", game) != null) {
                    GameManager.checkGameStatus("O", game)//?.let { showWinner(it) }
                    binding.GameIdText.text = "Loser!"
                    Holders.GameFinishedHolder.finished = true
                }
            }
            "O" -> {
                if (GameManager.checkGameStatus("O", game) != null) {
                    GameManager.checkGameStatus("O", game)//?.let { showWinner(it) }
                    binding.GameIdText.text = "Winner!"
                    Holders.GameFinishedHolder.finished = true
                }
                if (GameManager.checkGameStatus("X", game) != null) {
                    GameManager.checkGameStatus("X", game)//?.let { showWinner(it) }
                    binding.GameIdText.text = "Loser!"
                    Holders.GameFinishedHolder.finished = true
                }
            }
        }
        if (countCurrentBoard() == 9 && !Holders.GameFinishedHolder.finished) {
            binding.GameIdText.text = "Draw!"
        }
    }
}





