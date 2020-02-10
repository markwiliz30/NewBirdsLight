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
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentController
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.CurrentId.extensions.CurrentID
import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.common.CalendarFactory
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primedatepicker.PickType
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet
import com.example.blapp.collection.DayCollection
import com.example.blapp.collection.ScheduleCollection
import com.example.blapp.common.ManageDayDialog
import com.example.blapp.common.DayState
import com.example.blapp.model.DayManager
import kotlinx.android.synthetic.main.fragment_day_picker.*
import kotlinx.android.synthetic.main.fragment_day_picker.view.*
import kotlinx.android.synthetic.main.fragment_time_schedule.*
import kotlinx.android.synthetic.main.fragment_time_schedule.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DayPicker : Fragment(), PrimeDatePickerBottomSheet.OnDayPickedListener {

    lateinit var navController: NavController
    var parentPgmIndex: Int = 0
    lateinit var sMonth: String
    lateinit var sDay: String
    lateinit var eMonth: String
    lateinit var eDay: String
    var collection: DayManager? = null
     var Disabled: Boolean = false

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
       var filtered = DayCollection.dayCollection.filter { it.pgm!!.toInt() == parentPgmIndex }
        collection = filtered.find { it.pgm!!.toInt() == parentPgmIndex }


        if(collection!!.sMonth == "" && collection!!.sDay == "" && collection!!.eMonth == "" && collection!!.eDay == ""){
            Disabled = true
        }else{
            sMonth = collection!!.sMonth.toString()
            sDay = collection!!.sDay.toString()
            eMonth = collection!!.eMonth.toString()
            eDay = collection!!.eDay.toString()

        }


        btnMonday.setOnClickListener{
            if(Disabled){
                Toast.makeText(activity, "Please Set A Date Range" , Toast.LENGTH_SHORT).show()
            }else{
                collection!!.monday =! collection!!.monday
                ShowSaveAlert(1 ,collection!!.monday)
            }

        }

        btnTuesday.setOnClickListener{
            if(Disabled){
                Toast.makeText(activity, "Please Set A Date Range" , Toast.LENGTH_SHORT).show()
            }else {
                collection!!.tuesday = !collection!!.tuesday
                ShowSaveAlert(2, collection!!.tuesday)
            }
        }

        btnWednesday.setOnClickListener{
            if(Disabled){
                Toast.makeText(activity, "Please Set A Date Range" , Toast.LENGTH_SHORT).show()
            }else {
                collection!!.wednesday = !collection!!.wednesday
                ShowSaveAlert(3, collection!!.wednesday)
            }
        }
        btnThursday.setOnClickListener{
            if(Disabled){
                Toast.makeText(activity, "Please Set A Date Range" , Toast.LENGTH_SHORT).show()
            }else {
                collection!!.thursday = !collection!!.thursday
                ShowSaveAlert(4, collection!!.thursday)
            }
        }
        btnFriday.setOnClickListener{
            if(Disabled){
                Toast.makeText(activity, "Please Set A Date Range" , Toast.LENGTH_SHORT).show()
            }else {
                collection!!.friday = !collection!!.friday
                ShowSaveAlert(5, collection!!.friday)
            }
        }
        btnSaturday.setOnClickListener{
            if(Disabled){
                Toast.makeText(activity, "Please Set A Date Range" , Toast.LENGTH_SHORT).show()
            }else {
                collection!!.saturday = !collection!!.saturday
                ShowSaveAlert(6, collection!!.saturday)
            }
        }
        btnSunday.setOnClickListener{
            if(Disabled){
                Toast.makeText(activity, "Please Set A Date Range" , Toast.LENGTH_SHORT).show()
            }else {
                collection!!.sunday = !collection!!.sunday
                ShowSaveAlert(7, collection!!.sunday)
            }
        }

//        btnAll.setOnClickListener{
//            DayState.alldays =! DayState.alldays
//            if(DayState.alldays){
//                btnAll.setBackgroundResource(R.drawable.bottom_border)
//                SelectAllDays()
//            }else{
//                btnAll.setBackgroundResource(R.drawable.button_model)
//                DeselectAllDays()
//            }
//        }

        for(i in 1..7){
            BorderOrganize(i)
        }

        btn_set_calender.setOnClickListener{
            var datePicker: PrimeDatePickerBottomSheet?

            val calendarType = CalendarType.CIVIL

            val pickType = PickType.RANGE_START

            val minDateCalendar= null

            val maxDateCalendar = null

            val typeface = null

            val today = CalendarFactory.newInstance(calendarType)


            datePicker = PrimeDatePickerBottomSheet.newInstance(
                currentDateCalendar = today,
                minDateCalendar = minDateCalendar,
                maxDateCalendar = maxDateCalendar,
                pickType = pickType,
                typefacePath = typeface
            )
            datePicker?.setOnDateSetListener(this)
            datePicker?.show(activity!!.supportFragmentManager, "PrimeDatePickerBottomSheet")
        }
    }

//    fun SelectAllDays(){
//        btnMonday.setBackgroundResource(R.drawable.bottom_border)
//        isMondayClicked = true
//        btnTuesday.setBackgroundResource(R.drawable.bottom_border)
//        isTuesdayClicked = true
//        btnWednesday.setBackgroundResource(R.drawable.bottom_border)
//        isWednesdayClicked = true
//        btnThursday.setBackgroundResource(R.drawable.bottom_border)
//        isThursdayClicked = true
//        btnFriday.setBackgroundResource(R.drawable.bottom_border)
//        isFridayClicked = true
//        btnSaturday.setBackgroundResource(R.drawable.bottom_border)
//        isSaturdayClicked = true
//        btnSunday.setBackgroundResource(R.drawable.bottom_border)
//        isSundayClicked = true
//    }
//
//    fun DeselectAllDays(){
//        btnMonday.setBackgroundResource(R.drawable.button_model)
//        isMondayClicked = false
//        btnTuesday.setBackgroundResource(R.drawable.button_model)
//        isTuesdayClicked = false
//        btnWednesday.setBackgroundResource(R.drawable.button_model)
//        isWednesdayClicked = false
//        btnThursday.setBackgroundResource(R.drawable.button_model)
//        isThursdayClicked = false
//        btnFriday.setBackgroundResource(R.drawable.button_model)
//        isFridayClicked = false
//        btnSaturday.setBackgroundResource(R.drawable.button_model)
//        isSaturdayClicked = false
//        btnSunday.setBackgroundResource(R.drawable.button_model)
//        isSundayClicked = false
//    }

    fun ShowTimeSchedule(day: Int) {
        when (day) {
            1 -> {
                val bundle = bundleOf("parentPgmIndex" to  parentPgmIndex , "days" to day)
                navController.navigate(R.id.action_dayPicker_to_timeSchedule, bundle)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            2 -> {
                val bundle = bundleOf("parentPgmIndex" to  parentPgmIndex , "days" to day)
                navController.navigate(R.id.action_dayPicker_to_timeSchedule, bundle)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            3 -> {
                val bundle = bundleOf("parentPgmIndex" to  parentPgmIndex , "days" to day)
                navController.navigate(R.id.action_dayPicker_to_timeSchedule, bundle)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            4 -> {
                val bundle = bundleOf("parentPgmIndex" to  parentPgmIndex , "days" to day)
                navController.navigate(R.id.action_dayPicker_to_timeSchedule, bundle)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            5 -> {
                val bundle = bundleOf("parentPgmIndex" to  parentPgmIndex , "days" to day)
                navController.navigate(R.id.action_dayPicker_to_timeSchedule, bundle)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            6 -> {
                val bundle = bundleOf("parentPgmIndex" to  parentPgmIndex , "days" to day)
                navController.navigate(R.id.action_dayPicker_to_timeSchedule, bundle)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
            7 -> {
                val bundle = bundleOf("parentPgmIndex" to  parentPgmIndex , "days" to day)
                navController.navigate(R.id.action_dayPicker_to_timeSchedule, bundle)
                CurrentID.UpdateID(num = 7)
                CurrentID.Updatebool(x = true)

            }
        }

    }

    fun onCancelChangeStatus(day: Int){
        when (day){
            1->{
                collection!!.monday =! collection!!.monday
            }
            2->{
                collection!!.tuesday =! collection!!.tuesday
            }
            3->{
                collection!!.wednesday =! collection!!.wednesday
            }
            4->{
                collection!!.thursday =! collection!!.thursday
            }
            5->{
                collection!!.friday =! collection!!.friday
            }
            6->{
                collection!!.saturday =! collection!!.saturday
            }
            7->{
                collection!!.sunday =! collection!!.sunday
            }
        }
    }



    fun BorderOrganize(day: Int ){
        when (day){
            1->{
            if(collection!!.monday){
                btnMonday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                btnMonday.setBackgroundResource(R.drawable.button_model)
                }
            }
            2->{
                if(collection!!.tuesday){
                    btnTuesday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnTuesday.setBackgroundResource(R.drawable.button_model)
                }
            }
            3->{
                if(collection!!.wednesday){
                    btnWednesday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnWednesday.setBackgroundResource(R.drawable.button_model)
                }
            }
            4->{
                if(collection!!.thursday){
                    btnThursday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnThursday.setBackgroundResource(R.drawable.button_model)
                }
            }
            5->{
                if(collection!!.friday){
                    btnFriday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnFriday.setBackgroundResource(R.drawable.button_model)
                }
            }
            6->{
                if(collection!!.saturday){
                    btnSaturday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnSaturday.setBackgroundResource(R.drawable.button_model)
                }
            }
            7->{
                if(collection!!.sunday){
                    btnSunday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnSunday.setBackgroundResource(R.drawable.button_model)
                }
            }
        }

    }

    override fun onMultipleDaysPicked(multipleDays: List<PrimeCalendar>) {

    }

    override fun onRangeDaysPicked(startDay: PrimeCalendar, endDay: PrimeCalendar) {
        Disabled = false
        collection!!.sMonth = startDay.month.toString()
        collection!!.sDay = startDay.dayOfMonth.toString()
        collection!!.eMonth = endDay.month.toString()
        collection!!.eDay = endDay.dayOfMonth.toString()
    }

    override fun onSingleDayPicked(singleDay: PrimeCalendar) {

    }

    fun ShowSaveAlert(day: Int , Status: Boolean){

        val mAlertDialog = AlertDialog.Builder(activity!!)
        mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
        mAlertDialog.setMessage("Hentai hehe")
        when(Status){
            true->{
                mAlertDialog.setTitle("Set Day as Active?")
                mAlertDialog.setPositiveButton("Yes Let's Go!") { dialog, id ->
                    var checker = ScheduleCollection.scheduleCollection.filter {it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day }

                    if(checker.isEmpty()){
                        Toast.makeText(activity, "No Time Set" , Toast.LENGTH_SHORT).show()
                        ShowSaveAlert(day, Status)
                    }else{
                        BorderOrganize(day)
                    }


                }
            }
            false->{
                mAlertDialog.setTitle("Deactive Day?")
                mAlertDialog.setPositiveButton("Yes Let's Go!") { dialog, id ->
                    BorderOrganize(day)
                }
            }
        }


        mAlertDialog.setNegativeButton("Manage Schedule") { dialog, id ->
            onCancelChangeStatus(day)
            ShowTimeSchedule(day)
        }

        mAlertDialog.setOnCancelListener { onCancelChangeStatus(day) }
        mAlertDialog.show()
    }
}
