package com.example.blapp.common

import com.example.blapp.adapter.WifiAdapter
import com.example.blapp.model.WifiItem

object SharedWifiDetails {
    var selectedWifiIndex: Int? = -1
    var wifiList = ArrayList<WifiItem>()
    var sharedWifiAdapter: WifiAdapter? = null
}