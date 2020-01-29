package com.example.blapp.common

import android.os.Bundle
import android.os.Handler
import android.os.Message

import com.example.blapp.communication.Channel
import com.example.blapp.communication.OnSocketListener

import java.net.InetSocketAddress
import java.net.SocketException

class DeviceProtocol : Handler.Callback, OnSocketListener {
    var version: Byte = 0x01
    var byteDataLength = ByteArray(2)
    var signature = byteArrayOf(
        0xAA.toByte(),
        0x42.toByte(),
        0x49.toByte(),
        0x52.toByte(),
        0x44.toByte(),
        0x4C.toByte(),
        0x49.toByte(),
        0x47.toByte(),
        0x48.toByte(),
        0x54.toByte()
    )
    lateinit var data: ByteArray
    var command: Byte = 0
    lateinit var dataToSend: ByteArray
    var dataLength = 0
    private var address: InetSocketAddress? = null
    private var channel: Channel? = null
    private var handler: Handler? = null
    var postDelayedSendToModule = Handler()
    var canAccess = false
    var isRecognized = false
    var canSend = false
    fun startChannel(): String {
        address =
            InetSocketAddress(DeviceInformation.destinationIP, DeviceInformation.destinationPort)
        handler = Handler(this)
        return if (null == channel) {
            try {
                channel = Channel(this)
                channel!!.bind(DeviceInformation.sourcePort)
                channel!!.start()
                "Channel started"
            } catch (e: SocketException) {
                e.toString()
                //getActivity().finish();
            }
        } else "Channel is not null"
    }

    fun stopChannel() {
        if (null == channel) {
            channel!!.stop()
        }
    }

    private fun dataLengthCounterHex(byteCount: Int): ByteArray {
        var lsd: Byte = 0x00
        var msd: Byte = 0x00
        var counter = 0
        val returnedBytes = ByteArray(2)
        for (i in 1..byteCount) {
            lsd++
            counter++
            if (counter > 255) {
                msd++
                lsd = 0x00
            }
        }
        returnedBytes[0] = lsd
        returnedBytes[1] = msd
        return returnedBytes
    }

    private val run = Runnable {
        if (canAccess) {
            sendData()
        }
    }
    var sendToModule = Runnable {
        if (canSend) {
            sendAll()
            canSend = false
        }
    }
    private val postSendData = Runnable {
        if (canAccess) {
            sendData()
        }
    }
    private val postSendAck = Runnable {
        if (isRecognized) {
            if (isRecognized) {
                sendFinalAck()
            }
        }
    }

//    fun setData(lXData: Int, lYData: Int, lLData: Int): ByteArray {
//        var x: Byte = 0
//        var y: Byte = 0
//        var l: Byte = 0
//        for (i in 0 until lXData) {
//            x++
//        }
//        for (j in 0 until lYData) {
//            y++
//        }
//        for (k in 0 until lLData) {
//            l++
//        }
//        return byteArrayOf(x, y, l)
//    }

    private fun sendData() {
        byteDataLength = dataLengthCounterHex(dataLength)
        val length = 1 + byteDataLength.size + 1 + data.size
        dataToSend = ByteArray(length)
        var i = 0
        while (i < length) {
            dataToSend[i] = version
            i++
            var l = 0
            while (l < byteDataLength.size) {
                dataToSend[i] = byteDataLength[l]
                l++
                i++
            }
            dataToSend[i] = command
            i++
            var m = 0
            while (m < data.size) {
                dataToSend[i] = data[m]
                m++
                i++
            }
            channel!!.sendTo(address, dataToSend)
            i++
        }
    }

    private fun sendFinalAck() {
        val finalAcknowledment = ByteArray(2)
        val O: Byte = 0x4f
        val K: Byte = 0x4b
        finalAcknowledment[0] = O
        finalAcknowledment[1] = K
        channel!!.sendTo(address, finalAcknowledment)
    }

    private fun sendSignature() {
        channel!!.sendTo(address, signature)
    }

    private fun sendAll() {
        sendSignature()
        val postDelayedSendData = Handler()
        postDelayedSendData.postDelayed(postSendData, 100)
        val postDelayedSendAck = Handler()
        postDelayedSendAck.postDelayed(postSendAck, 200)
        canAccess = false
        isRecognized = false
    }

    fun transferData(exCommand: Byte, exData: ByteArray) {
        command = exCommand
        data = exData
        dataLength = data.size
        sendAll()
    }

    fun transferDataWithDelay(exCommand: Byte, exData: ByteArray) {
        command = exCommand
        data = exData
        dataLength = data.size
        canSend = true
        postDelayedSendToModule.postDelayed(sendToModule, 100)
    }

    override fun handleMessage(msg: Message): Boolean {
        val bundle = msg.data
        val text = bundle.getString("text")
        val firstChar = text!!.get(0) + ""
        val secondChar = text!!.get(1) + ""
        val thirdChar = text!!.get(2) + ""
        val receivedAuth = firstChar + secondChar + thirdChar
        val authComp = "AUD"
        val recogComp = "RED"
        if (receivedAuth.equals(authComp)) {
            canAccess = true
            isRecognized = true
        }
//        if (text == recogComp) {
//            isRecognized = true
//        }
        return false
    }

    override fun onReceived(msg: String) {
        val bundle = Bundle()
        bundle.putString("text", msg)
        val msg = Message()
        msg.data = bundle
        handler!!.sendMessage(msg)
    }
}
