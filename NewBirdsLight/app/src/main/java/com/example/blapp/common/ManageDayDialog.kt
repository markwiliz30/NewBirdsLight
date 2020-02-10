package com.example.blapp.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.blapp.R

class ManageDayDialog : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.layout_manage_day_dialog, null)

        builder.setView(view)
            .setTitle("Choose your option")
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(context, "Cancel pressed", Toast.LENGTH_SHORT).show()
            }
        return builder.create()
    }
}