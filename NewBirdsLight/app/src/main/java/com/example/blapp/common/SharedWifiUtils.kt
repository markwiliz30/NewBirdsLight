package com.example.blapp.common

import android.net.wifi.WifiManager
import com.example.blapp.adapter.WifiAdapter
import com.example.blapp.model.WifiItem

object SharedWifiUtils {
    var selectedWifiIndex: Int? = -1
    var wifiList = ArrayList<WifiItem>()
    var sharedWifiAdapter: WifiAdapter? = null
    var sharedWifiManager: WifiManager? = null
}