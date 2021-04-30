package com.example.noughtsandcrosses.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.noughtsandcrosses.GameManager
import com.example.noughtsandcrosses.databinding.DialogJoinGameBinding


class JoinGameDialog() : DialogFragment() {

    internal lateinit var listener:GameDialogListener

    // Android Bundle is used to pass data between activities.
    // The values that are to be passed are mapped to String keys
    // which are later used in the next activity to retrieve the values.
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogJoinGameBinding.inflate(inflater)

            builder.apply {
                setTitle("Join game")
                setPositiveButton("Join") { dialog, which ->
                    if(binding.username.text.toString() != "" && binding.gameId.text.toString() != ""){
                        //listener.onDialogCreateGame(binding.username.text.toString())
                        GameManager.joinGame(binding.username.text.toString(), binding.gameId.text.toString())
                    }
                }
                setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
                setView(binding.root)
            }

            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as GameDialogListener
        } catch (e:ClassCastException){
            throw ClassCastException(("$context must implement GameDialogListener"))

        }
    }

}