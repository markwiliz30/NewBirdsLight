package com.example.blapp


import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentController
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.CurrentId.extensions.CurrentID
import com.example.blapp.collection.ScheduleCollection
import kotlinx.android.synthetic.main.fragment_day_picker.*
import kotlinx.android.synthetic.main.fragment_day_picker.view.*
import kotlinx.android.synthetic.main.fragment_time_schedule.*
import kotlinx.android.synthetic.main.fragment_time_schedule.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DayPicker : Fragment() {

    lateinit var navController: NavController
    var parentPgmIndex: Int = 0
    var isMondayClicked: Boolean = false
    var isTuesdayClicked: Boolean = false
    var isWednesdayClicked: Boolean = false
    var isThursdayClicked: Boolean = false
    var isFridayClicked: Boolean = false
    var isSaturdayClicked: Boolean = false
    var isSundayClicked: Boolean = false
    var isSelectAllClicked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        parentPgmIndex = arguments!!.getInt("parentPgmIndex")
        return inflater.inflate(R.layout.fragment_day_picker, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnCancelDayPicker.setOnClickListener{
            navController.navigate(R.id.action_dayPicker_to_programFragment)
            CurrentID.UpdateID(num = 3)
            CurrentID.Updatebool(x = false)
        }

        btnMonday.setOnClickListener{
                ShowTimeSchedule(1)
        }

        btnTuesday.setOnClickListener{
            isTuesdayClicked =!isTuesdayClicked
            if(isTuesdayClicked){
                btnTuesday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnTuesday.setBackgroundResource(R.drawable.button_model)
            }
        }

        btnWednesday.setOnClickListener{
            isWednesdayClicked =!isWednesdayClicked
            if(isWednesdayClicked){
                btnWednesday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnWednesday.setBackgroundResource(R.drawable.button_model)
            }
        }
        btnThursday.setOnClickListener{
            isThursdayClicked =!isThursdayClicked
            if(isThursdayClicked){
                btnThursday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnThursday.setBackgroundResource(R.drawable.button_model)
            }
        }
        btnFriday.setOnClickListener{
            isFridayClicked =!isFridayClicked
            if(isFridayClicked){
                btnFriday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnFriday.setBackgroundResource(R.drawable.button_model)
            }
        }
        btnSaturday.setOnClickListener{
            isSaturdayClicked =!isSaturdayClicked
            if(isSaturdayClicked){
                btnSaturday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnSaturday.setBackgroundResource(R.drawable.button_model)
            }
        }
        btnSunday.setOnClickListener{
            isSundayClicked =!isSundayClicked
            if(isSundayClicked){
                btnSunday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnSunday.setBackgroundResource(R.drawable.button_model)
            }
        }

        btnAll.setOnClickListener{
            isSelectAllClicked =! isSelectAllClicked
            if(isSelectAllClicked){
                btnAll.setBackgroundResource(R.drawable.bottom_border)
                SelectAllDays()
            }else{
                btnAll.setBackgroundResource(R.drawable.button_model)
                DeselectAllDays()
            }
        }

        for(x in 1..7){
            BorderOrganize(x)
        }


    }

    fun SelectAllDays(){
        btnMonday.setBackgroundResource(R.drawable.bottom_border)
        isMondayClicked = true
        btnTuesday.setBackgroundResource(R.drawable.bottom_border)
        isTuesdayClicked = true
        btnWednesday.setBackgroundResource(R.drawable.bottom_border)
        isWednesdayClicked = true
        btnThursday.setBackgroundResource(R.drawable.bottom_border)
        isThursdayClicked = true
        btnFriday.setBackgroundResource(R.drawable.bottom_border)
        isFridayClicked = true
        btnSaturday.setBackgroundResource(R.drawable.bottom_border)
        isSaturdayClicked = true
        btnSunday.setBackgroundResource(R.drawable.bottom_border)
        isSundayClicked = true
    }

    fun DeselectAllDays(){
        btnMonday.setBackgroundResource(R.drawable.button_model)
        isMondayClicked = false
        btnTuesday.setBackgroundResource(R.drawable.button_model)
        isTuesdayClicked = false
        btnWednesday.setBackgroundResource(R.drawable.button_model)
        isWednesdayClicked = false
        btnThursday.setBackgroundResource(R.drawable.button_model)
        isThursdayClicked = false
        btnFriday.setBackgroundResource(R.drawable.button_model)
        isFridayClicked = false
        btnSaturday.setBackgroundResource(R.drawable.button_model)
        isSaturdayClicked = false
        btnSunday.setBackgroundResource(R.drawable.button_model)
        isSundayClicked = false
    }
    fun ShowTimeSchedule(day: Int) {
        when (day) {
            1 -> {
                val bundle = bundleOf("parentPgmIndex" to  parentPgmIndex , "days" to day)
                navController.navigate(R.id.action_dayPicker_to_timeSchedule, bundle)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            2 -> {
                navController.navigate(R.id.action_dayPicker_to_timeSchedule)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            3 -> {
                navController.navigate(R.id.action_dayPicker_to_timeSchedule)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            4 -> {
                navController.navigate(R.id.action_dayPicker_to_timeSchedule)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            5 -> {
                navController.navigate(R.id.action_dayPicker_to_timeSchedule)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            6 -> {
                navController.navigate(R.id.action_dayPicker_to_timeSchedule)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            7 -> {
                navController.navigate(R.id.action_dayPicker_to_timeSchedule)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
        }

    }

    fun DateChecker(day: Int): Boolean {
        var Checker = ScheduleCollection.scheduleCollection.filter { it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day }
        return !Checker.isEmpty()
    }

    fun BorderOrganize(day: Int){
        when (day){
            1->{
            if(DateChecker(day)){
                btnMonday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                btnMonday.setBackgroundResource(R.drawable.button_model)
                }
            }
            2->{
                if(DateChecker(day)){
                    btnTuesday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnTuesday.setBackgroundResource(R.drawable.button_model)
                }
            }
            3->{
                if(DateChecker(day)){
                    btnWednesday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnWednesday.setBackgroundResource(R.drawable.button_model)
                }
            }
            4->{
                if(DateChecker(day)){
                    btnThursday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnThursday.setBackgroundResource(R.drawable.button_model)
                }
            }
            5->{
                if(DateChecker(day)){
                    btnFriday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnFriday.setBackgroundResource(R.drawable.button_model)
                }
            }
            6->{
                if(DateChecker(day)){
                    btnSaturday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnSaturday.setBackgroundResource(R.drawable.button_model)
                }
            }
            7->{
                if(DateChecker(day)){
                    btnSunday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnSunday.setBackgroundResource(R.drawable.button_model)
                }
            }
        }

    }

}
