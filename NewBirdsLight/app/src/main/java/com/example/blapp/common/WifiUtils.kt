package com.example.blapp.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.widget.Toast
import com.example.blapp.adapter.WifiAdapter
import com.example.blapp.model.WifiItem

@Suppress("DEPRECATION")
class WifiUtils {
    companion object{
        var selectedWifiIndex: Int? = -1
        var wifiList = ArrayList<WifiItem>()
        var sharedWifiAdapter: WifiAdapter? = null
        var sharedWifiManager: WifiManager? = null
    }

    fun connectWiFi(wifiManager: WifiManager, SSID: String, password: String, Security: String) {
        try {
            val conf = WifiConfiguration()
            conf.SSID =
                "\"" + SSID + "\""   // Please note the quotes. String should contain ssid in quotes
            conf.status = WifiConfiguration.Status.ENABLED
            conf.priority = 40
            // Check if security type is WEP
            if (Security.toUpperCase().contains("WEP")) {
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
                conf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                conf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)

                if (password.matches("^[0-9a-fA-F]+$".toRegex())) {
                    conf.wepKeys[0] = password
                } else {
                    conf.wepKeys[0] = "\"" + password + "\""
                }

                conf.wepTxKeyIndex = 0
                // Check if security type is WPA
            } else if (Security.toUpperCase().contains("WPA")) {

                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)

                conf.preSharedKey = "\"" + password + "\""
                // Check if network is open network
            } else {
                conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
                conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
                conf.allowedAuthAlgorithms.clear()
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            }
            //Connect to the network
            val networkId = wifiManager.addNetwork(conf)

            val list = wifiManager.configuredNetworks
            for (i in list) {
                if (i.SSID != null && i.SSID == "\"" + SSID + "\"") {

                    val isDisconnected = wifiManager.disconnect()

                    val isEnabled = wifiManager.enableNetwork(i.networkId, true)

                    val isReconnected = wifiManager.reconnect()

                    break
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun IsWiFiConnected(context: Context, SSID: String)
    : Boolean {
        val connectivity = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(connectivity != null)
        {
            val info = connectivity.allNetworkInfo
            if(info != null)
            {
                for (i in info.indices) {
                    if (info[i].typeName == SSID && info[i].isConnected)
                        return true
                }
            }
        }
        return false
    }
}