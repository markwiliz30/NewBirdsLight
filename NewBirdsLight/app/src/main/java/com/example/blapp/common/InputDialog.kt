package com.example.blapp.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.blapp.R

class InputDialog : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.layout_input_dialog, null)

        builder.setView(view)
            .setTitle(SelectedDevice.SSID)
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(context, "Cancel pressed", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Connect") { dialog, which ->
                val connectWifi = WifiUtils()
                //val inputPassword: String? = input_password_box.text.toString()
                connectWifi.connectWiFi(SharedWifiUtils.sharedWifiManager!!, SelectedDevice.SSID, "12345678", SharedWifiUtils.wifiList[SharedWifiUtils.selectedWifiIndex!!].capabilities!!)
                SharedWifiUtils.wifiList[SharedWifiUtils.selectedWifiIndex!!].selected = true
                SharedWifiUtils.sharedWifiAdapter!!.notifyDataSetChanged()
                Toast.makeText(context, "Connect pressed", Toast.LENGTH_SHORT).show()
            }

        return builder.create()
    }
}