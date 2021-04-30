package com.example.noughtsandcrosses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.noughtsandcrosses.databinding.ActivityMainBinding
import com.example.noughtsandcrosses.dialogs.CreateGameDialog
import com.example.noughtsandcrosses.dialogs.GameDialogListener
import com.example.noughtsandcrosses.dialogs.JoinGameDialog

class MainActivity : AppCompatActivity(), GameDialogListener {

    val TAG:String = "MainActivity"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGameButton.setOnClickListener {
            createNewGame()
        }

        binding.joinGameButton.setOnClickListener {
            joinGame()
        }

    }

    private fun createNewGame(){
        val dlg = CreateGameDialog()
        dlg.show(supportFragmentManager,"CreateGameDialogFragment")
    }

    private fun joinGame(){
        val dlg = JoinGameDialog()
        dlg.show(supportFragmentManager,"JoinGameDialogFragment")
    }

    override fun onDialogCreateGame(player: String) {
        Log.d(TAG,player)
    }

    override fun onDialogJoinGame(player: String, gameId: String) {
        Log.d(TAG, "$player $gameId")
    }

}