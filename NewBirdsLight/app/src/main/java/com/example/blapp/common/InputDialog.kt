package com.example.blapp.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.blapp.R

class InputDialog : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.layout_input_dialog, null)
        val SelSSID = WifiUtils.wifiList[WifiUtils.selectedWifiIndex!!].name

        builder.setView(view)
            .setTitle(SelSSID)
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(context, "Cancel pressed", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton("Connect") { dialog, which ->
                val connectWifi = WifiUtils()
                //val inputPassword: String? = input_password_box.text.toString()
                connectWifi.connectWiFi(WifiUtils.sharedWifiManager!!, SelSSID!!, "12345678", WifiUtils.wifiList[WifiUtils.selectedWifiIndex!!].capabilities!!)
                WifiUtils.wifiList[WifiUtils.selectedWifiIndex!!].selected = true
                WifiUtils.sharedWifiAdapter!!.notifyDataSetChanged()
                //Toast.makeText(context, "Connect pressed", Toast.LENGTH_SHORT).show()
                val wifiUtils = WifiUtils()
                val selSSID = WifiUtils.wifiList.find { it.name ==  WifiUtils.wifiList[WifiUtils.selectedWifiIndex!!].name}
                selSSID!!.status = wifiUtils.IsWiFiConnected(context!!, selSSID!!.name!!)
            }

        return builder.create()
    }
}