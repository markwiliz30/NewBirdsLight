package com.example.blapp


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.blapp.common.DeviceProtocol
import com.example.blapp.common.Protocol
import kotlinx.android.synthetic.main.fragment_set_step.*
import kotlinx.android.synthetic.main.fragment_test.*
import kotlin.math.round

class TestFragment : Fragment() {

    lateinit var navController: NavController
    internal var pVal: Int = 0
    internal var tVal:Int = 0
    internal var bVal:Int = 0
    internal var ButtonStatus:Boolean = true
    internal var SeekBarStatus:Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        val command: Byte = 0x02
        val data = byteArrayOf(
            0x01.toByte(),
            0x01.toByte(),
            0.toByte(),
            0.toByte(),
            0.toByte(),
            0x01.toByte()
        )
        Protocol.cDeviceProt!!.transferData(command, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        var command: Byte = 0x02
        var data: ByteArray
        tglPgm1.setOnClickListener{

            if(ButtonStatus){
                data = byteArrayOf(
                    0x01.toByte(),
                    0x01.toByte(),
                    255.toByte(),
                    255.toByte(),
                    255.toByte(),
                    0x01.toByte()
                )
                Protocol.cDeviceProt!!.transferData(command, data)
                Toast.makeText(context, "Testing Program 1 Running", Toast.LENGTH_SHORT).show()
                SeekBarStatus = false
            }else{
                Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show()
            }

        }

        tglPgm2.setOnClickListener{
            if(ButtonStatus){
                data = byteArrayOf(
                    0x01.toByte(),
                    0x01.toByte(),
                    12.toByte(),
                    53.toByte(),
                    22.toByte(),
                    0x01.toByte()
                )
                Protocol.cDeviceProt!!.transferData(command, data)
                Toast.makeText(context, "Testing Program 2 Running", Toast.LENGTH_SHORT).show()
                SeekBarStatus = false
            }else{
                Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show()
            }
        }

        tglPgm3.setOnClickListener{
            if(ButtonStatus){
                    data = byteArrayOf(
                        0x01.toByte(),
                        0x01.toByte(),
                        128.toByte(),
                        128.toByte(),
                        128.toByte(),
                        0x01.toByte()
                    )
                    Protocol.cDeviceProt!!.transferData(command, data)


                Toast.makeText(context, "Testing Program 3 Running", Toast.LENGTH_SHORT).show()
                SeekBarStatus = false
            }else{
                Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show()
            }
        }

        btnReset.setOnClickListener{
            test_blink_sb.progress = 0
            test_pan_sb.progress = 128
            test_tilt_sb.progress =128
            data = byteArrayOf(
                0x01.toByte(),
                0x01.toByte(),
                128.toByte(),
                128.toByte(),
                0.toByte(),
                0x01.toByte()
            )
            Protocol.cDeviceProt!!.transferData(command, data)
            Toast.makeText(context, "Resetting the device", Toast.LENGTH_SHORT).show()
            ButtonStatus = true
            SeekBarStatus = true
        }

         fun updateTextOnPan(value: Int) {
             var passVal: Double = 0.0
             if(value == 255){
                 update_test_pan.text = "270"
             }
             else if(value == 128){
                 update_test_pan.text = "0"
             }
             else if(value >= 129){
                 passVal = (value-128) * 2.109375
                 update_test_pan.text = ""+passVal.toInt()
             }else if(value <= 127){
                 passVal = (128-value) * 2.109375
                 update_test_pan.text = "-"+passVal.toInt()
             }

         }

        test_pan_sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(SeekBarStatus){
                    ButtonStatus = false
                    pVal = progress
                data = byteArrayOf(
                    0x01.toByte(),
                    0x01.toByte(),
                    pVal.toByte(),
                    tVal.toByte(),
                    bVal.toByte(),
                    0x01.toByte()
                )
                Protocol.cDeviceProt?.transferDataWithDelay(command, data)

                    updateTextOnPan(pVal)
                }else{
                    Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show()
                    test_pan_sb.progress = 128

                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        fun updateTextOnTilt(value: Int) {
            var passVal: Double = 0.0
            if(value == 255){
                update_test_tilt.text = "90"
            }
            else if(value == 128){
                update_test_tilt.text = "0"
            }
            else if(value >= 129){
                passVal = (value-128) * 0.703125
                update_test_tilt.text = ""+passVal.toInt().toString()
            }else if(value <= 127){
                passVal = (128-value) * 0.703125
                update_test_tilt.text = "-"+passVal.toInt()
            }

        }
        test_tilt_sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(SeekBarStatus){
                    ButtonStatus = false
                    tVal = progress
                data = byteArrayOf(
                    0x01.toByte(),
                    0x01.toByte(),
                    pVal.toByte(),
                    tVal.toByte(),
                    bVal.toByte(),
                    0x01.toByte()
                )
                Protocol.cDeviceProt?.transferDataWithDelay(command, data)
                    updateTextOnTilt(tVal)
                }else{
                    Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show()
                    test_tilt_sb.progress = 128
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        test_blink_sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(SeekBarStatus){
                    ButtonStatus = false
                    bVal = progress
                data = byteArrayOf(
                    0x01.toByte(),
                    0x01.toByte(),
                    pVal.toByte(),
                    tVal.toByte(),
                    bVal.toByte(),
                    0x01.toByte()
                )
                Protocol.cDeviceProt?.transferDataWithDelay(command, data)
                    update_test_blink.text = bVal.toString()
                }else{
                    Toast.makeText(context, "Disabled", Toast.LENGTH_SHORT).show()
                    test_blink_sb.progress = 0
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
}
