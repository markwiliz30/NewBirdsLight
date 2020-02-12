package com.example.blapp

import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.CurrentId.extensions.CurrentID
import com.example.blapp.adapter.TimeAdapter
import com.example.blapp.collection.DayCollection
import com.example.blapp.collection.ScheduleCollection
import com.example.blapp.helper.MyButton
import com.example.blapp.helper.MySwipeHelper
import com.example.blapp.helper.MySwipeHelper2
import com.example.blapp.listener.MyButtonClickListener
import com.example.blapp.model.DayManager
import com.example.blapp.model.ScheduleItem
import kotlinx.android.synthetic.main.fragment_day_picker.*
import kotlinx.android.synthetic.main.fragment_time_list.*
import kotlinx.android.synthetic.main.fragment_time_schedule.*
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimeSchedule : Fragment() {

    lateinit var navController: NavController
    private var parentPgmIndex: Int = 0
    private var day: Int = 0
    private var tempshour: Int = 25
    private var tempsminute: Int = 25
    private var tempehour: Int = 25
    private var tempeminute: Int = 25
    var conflicts: Boolean = true
    lateinit var adapter: TimeAdapter
    lateinit var layoutManager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        parentPgmIndex = arguments!!.getInt("parentPgmIndex")
        day = arguments!!.getInt("days")
        return inflater.inflate(R.layout.fragment_time_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        lst_time.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity)
        lst_time.layoutManager = layoutManager

        adapter = TimeAdapter(activity, ScheduleCollection.scheduleCollection)
        lst_time.adapter = adapter

        val mTimePickerStart: TimePickerDialog
        val mTimePickerEnd: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

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

            if(tempehour == 25 && tempeminute == 25 && tempshour == 25 && tempsminute == 25){
                btn_save_time.startAnimation(AnimationUtils.loadAnimation(activity , R.anim.shake))
                Toast.makeText(activity, "No time set" , Toast.LENGTH_LONG).show()
            }else if (tempeminute == 25 && tempehour == 25){
                btn_save_time.startAnimation(AnimationUtils.loadAnimation(activity , R.anim.shake))
                Toast.makeText(activity, "End Time is not Set" , Toast.LENGTH_LONG).show()
            }else if(tempshour == tempehour && tempeminute == tempsminute){
                btn_save_time.startAnimation(AnimationUtils.loadAnimation(activity , R.anim.shake))
                Toast.makeText(activity, "Invalid Time Set" , Toast.LENGTH_LONG).show()
            }
            else{
                if(!timeConflict()){
                    btn_save_time.startAnimation(AnimationUtils.loadAnimation(activity , R.anim.shake))
                    Toast.makeText(activity, "Time has Conflict" , Toast.LENGTH_LONG).show()
                }else{
                    if(day == 8){
                        addAllDaysCollection()
                    }else{
                        addToTimeCollection()
                    }

                    //refreshList()
                    returnToInitial()
                    Toast.makeText(activity, "Save Success" , Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun addToTimeCollection(){
        val newItem = ScheduleItem()
        var filtered =  ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day }
        var DateFind = DayCollection.dayCollection.find { it.pgm!!.toInt() == parentPgmIndex }
        val schedNumber = filtered.count()+1
        newItem.command = 0x02
        newItem.pgm = parentPgmIndex.toByte()
        newItem.shour = tempshour.toByte()
        newItem.sminute = tempsminute.toByte()
        newItem.ehour = tempehour.toByte()
        newItem.eminute = tempeminute.toByte()
        newItem.sched = schedNumber.toByte()
        newItem.wday = day.toByte()
        newItem.smonth = DateFind!!.sMonth!!.toByte()
        newItem.sday = DateFind!!.sDay!!.toByte()
        newItem.emonth= DateFind!!.eMonth!!.toByte()
        newItem.eday = DateFind!!.eDay!!.toByte()
        ScheduleCollection.scheduleCollection.add(newItem)
    }

    private fun returnToInitial(){
        time_start.text = "Set Time"
        time_end.text = "Set Time"
        btn_End_Time.isEnabled = false
        tempehour = 25
        tempeminute = 25
        tempshour = 25
        tempsminute = 25
    }

//    private fun deleteTime(pos: Int){
//        var filtered =  ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day }
//        var Del = filtered.find { it.sched!!.toInt() == pos }
//
//        ScheduleCollection.scheduleCollection.remove(Del)
//
//
//        for(update in ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day }){
//            if(update.sched!!.toInt() > Del!!.sched!!.toInt()){
//                update.sched = update.sched!!.dec()
//            }
//        }
//    }

    fun ShowDeleteAlert(schd: Int){

        val mAlertDialog = AlertDialog.Builder(activity!!)
        mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
        mAlertDialog.setTitle("Are you sure?")
        mAlertDialog.setMessage("Do you want to delete this schedule " + (schd + 1) + "?")

        mAlertDialog.setPositiveButton("Yes") { dialog, id ->

        }

        mAlertDialog.setNegativeButton("No") { dialog, id ->

        }

        mAlertDialog.show()
    }

    fun timeConflict():Boolean{
        var tempStime: Int = 0
        var tempEtime: Int = 0
        conflicts = true
        var TimeRestric = ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex }

        tempStime = if(tempsminute < 10){
            (""+tempshour+"0"+tempsminute+"").toInt()
        }else{
            (""+tempshour+tempsminute+"").toInt()
        }

        tempEtime = if(tempeminute < 10){
            (""+tempehour+"0"+tempeminute+"").toInt()
        }else{
            (""+tempehour+tempeminute+"").toInt()
        }

        if(tempStime > tempEtime){
            conflicts = false
        }

        for (time in TimeRestric){
            var tstart =(""+time.shour+time.sminute+"").toInt()
            var tend = (""+time.ehour+time.eminute+"").toInt()

          for(x in tstart..tend){
                    if(tempStime == x || tempEtime == x){
                        conflicts = false
                    }
                }
            }
        return conflicts
    }

    private fun addAllDaysCollection(){

        var DateFind = DayCollection.dayCollection.find { it.pgm!!.toInt() == parentPgmIndex }

       for(x in 1..8){
           val newItem = ScheduleItem()
           var timeCount = ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == x }
           newItem.command = 0x02
           newItem.pgm = parentPgmIndex.toByte()
           newItem.shour = tempshour.toByte()
           newItem.sminute = tempsminute.toByte()
           newItem.ehour = tempehour.toByte()
           newItem.eminute = tempeminute.toByte()
           newItem.sched = timeCount.count().toByte().inc()
           newItem.wday = x.toByte()
           newItem.smonth = DateFind!!.sMonth!!.toByte()
           newItem.sday = DateFind!!.sDay!!.toByte()
           newItem.emonth= DateFind!!.eMonth!!.toByte()
           newItem.eday = DateFind!!.eDay!!.toByte()
           ScheduleCollection.scheduleCollection.add(newItem)
       }
    }
}
