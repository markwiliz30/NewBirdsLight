package com.example.blapp

import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.CurrentId.extensions.CurrentID
import kotlinx.android.synthetic.main.fragment_time_schedule.*
import java.util.*

class TimeSchedule : Fragment() {

    lateinit var navController: NavController
    private var parentPgmIndex: Int = 0
    private var day: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        parentPgmIndex = arguments!!.getInt("parentPgmIndex")
        day = arguments!!.getInt("day")
        return inflater.inflate(R.layout.fragment_time_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val mTimePickerStart: TimePickerDialog
        val mTimePickerEnd: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)


        mTimePickerEnd = TimePickerDialog(activity, object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                time_end.text= "To: "+hourOfDay.toString()+":"+minute.toString()
            }
        }, hour , minute , false)


        mTimePickerStart = TimePickerDialog(activity, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                time_start.text = "From: "+hourOfDay.toString()+":"+minute.toString()
                btn_End_Time.isEnabled = true
            }

        }, hour, minute, false)

//        btn_Start_Time.setOnClickListener{
//            mTimePickerStart.show()}

//        btn_End_Time.setOnClickListener{
//            mTimePickerEnd.show()
//        }
    }

}
