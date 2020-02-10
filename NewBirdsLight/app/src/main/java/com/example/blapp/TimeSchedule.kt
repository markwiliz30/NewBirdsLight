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
import com.example.blapp.collection.ScheduleCollection
import com.example.blapp.helper.MyButton
import com.example.blapp.helper.MySwipeHelper
import com.example.blapp.listener.MyButtonClickListener
import com.example.blapp.model.DayManager
import com.example.blapp.model.ScheduleItem
import kotlinx.android.synthetic.main.fragment_day_picker.*
import kotlinx.android.synthetic.main.fragment_time_schedule.*
import java.util.*

class TimeSchedule : Fragment() {

    lateinit var navController: NavController
    lateinit var layoutManager: LinearLayoutManager
    private var parentPgmIndex: Int = 0
    private var day: Int = 0
    lateinit var adapter: TimeAdapter
    private var tempshour: Int = 25
    private var tempsminute: Int = 25
    private var tempehour: Int = 25
    private var tempeminute: Int = 25


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

            if(tempehour == 25 && tempeminute == 25 && tempshour == 25 && tempsminute == 25){
                btn_save_time.startAnimation(AnimationUtils.loadAnimation(activity , R.anim.shake))
                Toast.makeText(activity, "No time set" , Toast.LENGTH_LONG).show()
            }else if (tempeminute == 25 && tempehour == 25){
                btn_save_time.startAnimation(AnimationUtils.loadAnimation(activity , R.anim.shake))
                Toast.makeText(activity, "End Time is not Set" , Toast.LENGTH_LONG).show()
            }
            else{
                addToTimeCollection()
                refreshList()
                returnToInitial()
                Toast.makeText(activity, "Save Success" , Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_time.setHasFixedSize(true)
        recycler_time.setItemViewCacheSize(25)
        layoutManager = LinearLayoutManager(activity)
        recycler_time.layoutManager = layoutManager
        val swipe = object: MySwipeHelper(activity, recycler_time, 200)
        {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(
                    MyButton(activity,
                        "Delete",
                        30,
                        R.drawable.ic_delete_dark_blue_24dp,
                        Color.parseColor("#14BED1"),
                        object : MyButtonClickListener{
                            override fun onClick(pos: Int) {
                                ShowDeleteAlert(pos)
                            }
                        }
                    )
                )
            }
        }

        generateItem()
    }
    private fun generateItem() {
        adapter = TimeAdapter(activity,
            ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day } as MutableList<ScheduleItem>)
        recycler_time.adapter = adapter
    }

    private fun addToTimeCollection(){
        val newItem = ScheduleItem()
        var filtered =  ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day }
        val schedNumber = filtered.count()+1
        newItem.command = 0x02
        newItem.pgm = parentPgmIndex.toByte()
        newItem.shour = tempshour.toByte()
        newItem.sminute = tempsminute.toByte()
        newItem.ehour = tempehour.toByte()
        newItem.eminute = tempeminute.toByte()
        newItem.sched = schedNumber.toByte()
        newItem.wday = day.toByte()
        ScheduleCollection.scheduleCollection.add(newItem)
    }
    private fun refreshList(){
        adapter.itemList.clear()
        generateItem()
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
    private fun deleteTime(pos: Int){
        var filtered =  ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day }
        var Del = filtered.find { it.sched!!.toInt() == pos }

        ScheduleCollection.scheduleCollection.remove(Del)


        for(update in ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day }){
            if(update.sched!!.toInt() > Del!!.sched!!.toInt()){
                update.sched = update.sched!!.dec()
            }
        }
    }

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

}
