package com.example.blapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.CurrentId.extensions.CurrentID
import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.common.CalendarFactory
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primedatepicker.PickType
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet
import com.example.blapp.adapter.TimeAdapter
import com.example.blapp.collection.DayCollection
import com.example.blapp.collection.ScheduleCollection
import com.example.blapp.model.DayManager
import kotlinx.android.synthetic.main.fragment_day_picker.*

/**
 * A simple [Fragment] subclass.
 */
class DayPicker : Fragment(), PrimeDatePickerBottomSheet.OnDayPickedListener {

    lateinit var navController: NavController
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: TimeAdapter
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
            btn_set_calender.text = "$sMonth/$sDay ~ $eMonth/$eDay"
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

        btnAll.setOnClickListener{
            if(Disabled){
                Toast.makeText(activity , "Please Set A Date Range" , Toast.LENGTH_SHORT).show()
            }else{
                collection!!.alldays = !collection!!.alldays
                ShowSaveAlert(8 , collection!!.alldays)
            }

        }

        for(i in 1..7){
            initialOrganize(i)
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

    fun SelectAllDays(){
        btnMonday.setBackgroundResource(R.drawable.bottom_border)
        collection!!.monday = true
        btnTuesday.setBackgroundResource(R.drawable.bottom_border)
        collection!!.tuesday = true
        btnWednesday.setBackgroundResource(R.drawable.bottom_border)
        collection!!.wednesday = true
        btnThursday.setBackgroundResource(R.drawable.bottom_border)
        collection!!.thursday =true
        btnFriday.setBackgroundResource(R.drawable.bottom_border)
        collection!!.friday = true
        btnSaturday.setBackgroundResource(R.drawable.bottom_border)
        collection!!.saturday = true
        btnSunday.setBackgroundResource(R.drawable.bottom_border)
        collection!!.sunday = true
        btnAll.setBackgroundResource(R.drawable.bottom_border)
    }

    fun DeselectAllDays(){
        btnMonday.setBackgroundResource(R.drawable.button_model)
        collection!!.monday = false
        btnTuesday.setBackgroundResource(R.drawable.button_model)
        collection!!.tuesday = false
        btnWednesday.setBackgroundResource(R.drawable.button_model)
        collection!!.wednesday =false
        btnThursday.setBackgroundResource(R.drawable.button_model)
        collection!!.thursday = false
        btnFriday.setBackgroundResource(R.drawable.button_model)
        collection!!.friday = false
        btnSaturday.setBackgroundResource(R.drawable.button_model)
        collection!!.saturday = false
        btnSunday.setBackgroundResource(R.drawable.button_model)
        collection!!.sunday = false
        btnAll.setBackgroundResource(R.drawable.button_model)
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

            8 -> {
               val bundle = bundleOf("parentPgmIndex" to parentPgmIndex , "days" to day)
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
            8->{
                collection!!.alldays =! collection!!.alldays
            }
        }
    }



    fun initialOrganize(day: Int){
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

    fun BorderOrganize(day: Int ){
        when (day){
            1->{
                if(collection!!.monday){
                    btnMonday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnMonday.setBackgroundResource(R.drawable.button_model)
                        btnAll.setBackgroundResource(R.drawable.button_model)

                }
            }
            2->{
                if(collection!!.tuesday){
                    btnTuesday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnTuesday.setBackgroundResource(R.drawable.button_model)
                        btnAll.setBackgroundResource(R.drawable.button_model)

                }
            }
            3->{
                if(collection!!.wednesday){
                    btnWednesday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnWednesday.setBackgroundResource(R.drawable.button_model)
                        btnAll.setBackgroundResource(R.drawable.button_model)

                }
            }
            4->{
                if(collection!!.thursday){
                    btnThursday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnThursday.setBackgroundResource(R.drawable.button_model)
                        btnAll.setBackgroundResource(R.drawable.button_model)

                }
            }
            5->{
                if(collection!!.friday){
                    btnFriday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnFriday.setBackgroundResource(R.drawable.button_model)
                        btnAll.setBackgroundResource(R.drawable.button_model)

                }
            }
            6->{
                if(collection!!.saturday){
                    btnSaturday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnSaturday.setBackgroundResource(R.drawable.button_model)
                        btnAll.setBackgroundResource(R.drawable.button_model)

                }
            }
            7->{
                if(collection!!.sunday){
                    btnSunday.setBackgroundResource(R.drawable.bottom_border)
                }else{
                    btnSunday.setBackgroundResource(R.drawable.button_model)
                        btnAll.setBackgroundResource(R.drawable.button_model)

                }
            }
            8->{
                if(collection!!.alldays){
                    SelectAllDays()
                }else{
                    DeselectAllDays()
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

        btn_set_calender.text = startDay.month.toString()+"/"+startDay.dayOfMonth+" ~ "+endDay.month+"/"+endDay.dayOfMonth


        Toast.makeText(activity , collection!!.sMonth+collection!!.sDay+collection!!.eMonth+collection!!.eDay , Toast.LENGTH_SHORT).show()
    }

    override fun onSingleDayPicked(singleDay: PrimeCalendar) {

    }

    fun ShowSaveAlert(day: Int , Status: Boolean){

        var dayState = "Enable"
        val mAlertDialog = AlertDialog.Builder(activity!!)
        mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
        mAlertDialog.setTitle("Choose Option")

        when(Status)
        {
            true->{

                dayState = "Enable"
            }
            false->{
                dayState = "Disable"
            }
        }

        mAlertDialog.setPositiveButton(dayState) { dialog, id ->
            var checker = ScheduleCollection.scheduleCollection.filter {it.pgm!!.toInt() == parentPgmIndex && it.wday!!.toInt() == day }

            if(checker.isEmpty()){
                Toast.makeText(activity, "No Time Set" , Toast.LENGTH_SHORT).show()
               // collection!!.alldays =! collection!!.alldays
                ShowSaveAlert(day, Status)
            }else{
                BorderOrganize(day)
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
