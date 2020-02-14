package com.example.blapp.common

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Handler
import android.widget.Toast
import com.example.blapp.R
import com.example.blapp.adapter.WifiAdapter
import com.example.blapp.model.WifiItem
import dmax.dialog.SpotsDialog
import android.net.wifi.WifiInfo
import androidx.core.content.ContextCompat.getSystemService
import android.content.IntentFilter
import android.content.Intent
import java.io.IOException
import java.lang.ArithmeticException


@Suppress("DEPRECATION")
class WifiUtils {

    companion object{
        var selectedWifiIndex: Int? = -1
        var wifiList = ArrayList<WifiItem>()
        var sharedWifiAdapter: WifiAdapter? = null
        var sharedWifiManager: WifiManager? = null
        var isConnectedToBL = false
    }

    fun checkIfConnectedToBL(){
        for(item in wifiList){
            if(item.status == 2)
            {
                item.selected = true
                isConnectedToBL = true
                return
            }
            item.selected = false
            isConnectedToBL = false
        }
    }

    fun connectWiFi(context: Context ,wifiManager: WifiManager, SSID: String, password: String, Security: String): Boolean {
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

                    val handler = Handler()
                    handler.postDelayed({
                        val deviceProtocol = DeviceProtocol()
                        Protocol.cDeviceProt = deviceProtocol

                        val isSSIDConnected = IsWiFiConnected(context, SSID)
                        if(isSSIDConnected)
                        {
//                            try {
//                                Protocol.cDeviceProt.stopChannel()
//                            }catch (e: java.lang.Exception) {
//
//                            }
                            Protocol.cDeviceProt.startChannel()
                            Toast.makeText(context, "Connected to " + SSID, Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            handler.postDelayed({
                                val innerIsSSIDConnected = IsWiFiConnected(context, SSID)
                                if(innerIsSSIDConnected)
                                {
//                                    try {
//                                        Protocol.cDeviceProt.stopChannel()
//                                    }catch (e: java.lang.Exception) {
//
//                                    }
                                    Protocol.cDeviceProt.startChannel()
                                    Toast.makeText(context, "Connected to " + SSID, Toast.LENGTH_SHORT).show()
                                }
                                else
                                {
                                    Toast.makeText(context, "Failed to connect to " + SSID, Toast.LENGTH_SHORT).show()
                                }
                            }, 1500)
                        }
                    }, 4000)

                    return isReconnected
                    //break
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return false
    }

    fun IsWiFiConnected(context: Context, SSID: String)
    : Boolean {
        val wifiInfo = sharedWifiManager!!.connectionInfo

        val holdWifiInfo = WifiInfo.getDetailedStateOf(wifiInfo.supplicantState)

        if (holdWifiInfo == NetworkInfo.DetailedState.OBTAINING_IPADDR || holdWifiInfo == NetworkInfo.DetailedState.CONNECTED) {
            if(wifiInfo.ssid == "\"" + SSID + "\"")
            {
                return true
            }
        }

//        val connectivity = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        if(connectivity != null)
//        {
//            val info = connectivity.allNetworkInfo
//            if(info != null)
//            {
//                for (i in info.indices) {
//                    if (info[i].typeName == SSID && info[i].isConnected)
//                    {
//                        return true
//                    }
//                }
//            }
//        }
        return false
    }

//    fun getWifiSSID(context: Context?): String {
//        if (context == null) {
//            return ""
//        }
//        val intent =
//            context.registerReceiver(null, IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION))
//        if (intent != null) {
//            val wifiInfo = intent.getParcelableExtra<WifiInfo>(WifiManager.EXTRA_WIFI_INFO)
//            if (wifiInfo != null) {
//                val ssid = wifiInfo.ssid
//                if (ssid != null) {
//                    return ssid
//                }
//            }
//        }
//        return ""
//    }

//    fun FindConnedtedSSID(context: Context)
//            : String {
//        val connectivity = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        if(connectivity != null)
//        {
//            val info = connectivity.allNetworkInfo
//            if(info != null)
//            {
//                for (i in info.indices) {
//                    if (info[i].isConnected)
//                    {
//                        val BLSSID = wifiList.find { it.name == info[i].typeName }
//                        if(BLSSID != null)
//                        {
//                            return BLSSID!!.name.toString()
//                        }
//                    }
//                }
//            }
//        }
//        return ""
//    }
}