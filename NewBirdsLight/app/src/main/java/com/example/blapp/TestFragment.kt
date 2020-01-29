package com.example.blapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.blapp.common.Protocol
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {

    lateinit var navController: NavController
    internal var pVal: Int = 0
    internal var tVal:Int = 0
    internal var bVal:Int = 0

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
        Protocol.cDeviceProt.transferData(command, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        var command: Byte = 0x02
        var data: ByteArray
        tglPgm1.setOnClickListener{
            data = byteArrayOf(
                0x01.toByte(),
                0x01.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                0x01.toByte()
            )
            Protocol.cDeviceProt.transferData(command, data)
            Toast.makeText(context, "Testing Program 1 Running", Toast.LENGTH_SHORT).show()
        }

        tglPgm2.setOnClickListener{
            data = byteArrayOf(
                0x01.toByte(),
                0x01.toByte(),
                0.toByte(),
                0.toByte(),
                0.toByte(),
                0x01.toByte()
            )
            Protocol.cDeviceProt.transferData(command, data)
            Toast.makeText(context, "Testing Program 2 Running", Toast.LENGTH_SHORT).show()
        }

        tglPgm3.setOnClickListener{
            data = byteArrayOf(
                0x01.toByte(),
                0x01.toByte(),
                0x80.toByte(),
                0x80.toByte(),
                0x80.toByte(),
                0x01.toByte()
            )
            Protocol.cDeviceProt.transferDataWithDelay(command, data)
            Toast.makeText(context, "Testing Program 3 Running", Toast.LENGTH_SHORT).show()
        }

        btnReset.setOnClickListener{
            Toast.makeText(context, "Resetting the device", Toast.LENGTH_SHORT).show()
        }

        test_pan_sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                pVal = progress
                data = byteArrayOf(
                    0x01.toByte(),
                    0x01.toByte(),
                    pVal.toByte(),
                    tVal.toByte(),
                    bVal.toByte(),
                    0x01.toByte()
                )
                Protocol.cDeviceProt.transferDataWithDelay(command, data)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        test_tilt_sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tVal = progress
                data = byteArrayOf(
                    0x01.toByte(),
                    0x01.toByte(),
                    pVal.toByte(),
                    tVal.toByte(),
                    bVal.toByte(),
                    0x01.toByte()
                )
                Protocol.cDeviceProt.transferDataWithDelay(command, data)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        test_blink_sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                bVal = progress
                data = byteArrayOf(
                    0x01.toByte(),
                    0x01.toByte(),
                    pVal.toByte(),
                    tVal.toByte(),
                    bVal.toByte(),
                    0x01.toByte()
                )
                Protocol.cDeviceProt.transferDataWithDelay(command, data)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
}
