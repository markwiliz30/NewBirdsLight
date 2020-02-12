package com.example.blapp.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.blapp.R
import com.example.blapp.collection.ScheduleCollection
import com.example.blapp.model.ScheduleItem



class TimeAdapter(internal var context: FragmentActivity? , internal var itemList:MutableList<ScheduleItem>):
RecyclerView.Adapter<TimeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.fragment_time_list, parent , false)
        return TimeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        var timeHold: Int? = itemList[position].sched!!.toInt()
        holder.txt_num.text = timeHold.toString()+"."
        holder.txt_title.text = itemList[position].shour.toString()+":"+itemList[position].sminute.toString() +"~"+itemList[position].ehour.toString()+":"+itemList[position].eminute.toString()
        holder.btn_del.setOnClickListener{
            DeleteAlert(itemList[position].pgm!!, itemList[position].wday!!, (position + 1).toByte())
        }
    }

    fun DeleteTime(pgm: Byte, day: Byte ,sched : Byte){

        val filteredSched = ScheduleCollection.scheduleCollection.filter { it.pgm == pgm && it.wday == day}
        val item = filteredSched.find { it.sched == sched }

        ScheduleCollection.scheduleCollection.remove(item)

        for(update in ScheduleCollection.scheduleCollection.filter { it.pgm == pgm && it.wday == day }){
            if(update.sched!!.toInt() > item!!.sched!!.toInt()){
                update.sched = update.sched!!.dec()
            }
        }
    }

    fun RefreshList(pgm: Byte, day: Byte){
        itemList.clear()
        itemList.addAll(ScheduleCollection.scheduleCollection.filter { it.pgm == pgm && it.wday == day })
        notifyDataSetChanged()
    }

    private fun DeleteAlert(pgm: Byte, day: Byte ,sched : Byte){
        val mAlertDialog = AlertDialog.Builder(context)
        mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
        mAlertDialog.setTitle("Are you sure?") //set alertdialog title
        mAlertDialog.setMessage("Do you want to delete schedule " + sched + "?" ) //set alertdialog message
        mAlertDialog.setPositiveButton("Yes") { dialog, id ->
            DeleteTime(pgm, day, sched)
            RefreshList(pgm, day)
        }
        mAlertDialog.setNegativeButton("No") { dialog, id ->

        }
        mAlertDialog.show()
    }
}