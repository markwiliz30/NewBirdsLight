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
import androidx.recyclerview.widget.LinearLayoutManager
import com.CurrentId.extensions.CurrentID
import com.example.blapp.adapter.TimeAdapter
import com.example.blapp.collection.ScheduleCollection
import com.example.blapp.model.ScheduleItem
import kotlinx.android.synthetic.main.fragment_time_schedule.*
import java.util.*

class TimeSchedule : Fragment() {

    lateinit var navController: NavController
    lateinit var layoutManager: LinearLayoutManager
    private var parentPgmIndex: Int = 0
    private var day: Int = 0
    lateinit var adapter: TimeAdapter
    private var tempshour: Int = 0
    private var tempsminute: Int = 0
    private var tempehour: Int = 0
    private var tempeminute: Int = 0


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
        var SchedTempList: MutableList<ScheduleItem> = mutableListOf()

        mTimePickerEnd = TimePickerDialog(activity, object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                time_end.text= "To: "+hourOfDay.toString()+":"+minute.toString()
                tempehour = hourOfDay
                tempeminute = minute
            }
        }, hour , minute , false)


        mTimePickerStart = TimePickerDialog(activity, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                time_start.text = "From: "+hourOfDay.toString()+":"+minute.toString()
                tempshour = hourOfDay
                tempsminute = minute

                btn_End_Time.isEnabled = true
            }

        }, hour, minute, false)

        btn_Start_Time.setOnClickListener{
            mTimePickerStart.show()}

        btn_End_Time.setOnClickListener{
            mTimePickerEnd.show()
        }
        btn_save_time.setOnClickListener{
            val newItem = ScheduleItem()
            var filtered =  ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex  }
            val schedNumber = filtered.count()+1
            var schedCounter: Int = 1
            newItem.command = 0x02
            newItem.pgm = parentPgmIndex.toByte()
            newItem.shour = tempshour.toByte()
            newItem.sminute = tempsminute.toByte()
            newItem.ehour = tempehour.toByte()
            newItem.eminute = tempeminute.toByte()
            newItem.sched = schedNumber.toByte()
            ScheduleCollection.scheduleCollection.add(newItem)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_time.setHasFixedSize(true)
        recycler_time.setItemViewCacheSize(25)
        layoutManager = LinearLayoutManager(activity)
        recycler_time.layoutManager = layoutManager
        generateItem()
    }
    private fun generateItem() {
        adapter = TimeAdapter(activity,
            ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex } as MutableList<ScheduleItem>)
        recycler_time.adapter = adapter
    }

}
