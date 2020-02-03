package com.example.blapp


import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.FragmentController
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.CurrentId.extensions.CurrentID
import kotlinx.android.synthetic.main.fragment_day_picker.*
import kotlinx.android.synthetic.main.fragment_time_picker.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DayPicker : Fragment() {

    lateinit var navController: NavController
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

        val mTimePickerStart: TimePickerDialog
        val mTimePickerEnd: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)


        mTimePickerEnd = TimePickerDialog(activity, object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

            }
        }, hour , minute , false)


        mTimePickerStart = TimePickerDialog(activity, R.style.MyTimePickerTheme,object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                mTimePickerEnd.show()
               // btn_End_Time.isEnabled = true
            }

        }, hour, minute, false)


        btnMonday.setOnClickListener{
            isMondayClicked =!isMondayClicked
            if(isMondayClicked){
                mTimePickerStart.show()
                btnMonday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnMonday.setBackgroundResource(R.drawable.button_model)
            }
        }

        btnTuesday.setOnClickListener{
            isTuesdayClicked =!isTuesdayClicked
            if(isTuesdayClicked){
                mTimePickerStart.show()
                btnTuesday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnTuesday.setBackgroundResource(R.drawable.button_model)
            }
        }

        btnWednesday.setOnClickListener{
            isWednesdayClicked =!isWednesdayClicked
            if(isWednesdayClicked){
                mTimePickerStart.show()
                btnWednesday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnWednesday.setBackgroundResource(R.drawable.button_model)
            }
        }
        btnThursday.setOnClickListener{
            isThursdayClicked =!isThursdayClicked
            if(isThursdayClicked){
                mTimePickerStart.show()
                btnThursday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnThursday.setBackgroundResource(R.drawable.button_model)
            }
        }
        btnFriday.setOnClickListener{
            isFridayClicked =!isFridayClicked
            if(isFridayClicked){
                mTimePickerStart.show()
                btnFriday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnFriday.setBackgroundResource(R.drawable.button_model)
            }
        }
        btnSaturday.setOnClickListener{
            isSaturdayClicked =!isSaturdayClicked
            if(isSaturdayClicked){
                mTimePickerStart.show()
                btnSaturday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnSaturday.setBackgroundResource(R.drawable.button_model)
            }
        }
        btnSunday.setOnClickListener{
            isSundayClicked =!isSundayClicked
            if(isSundayClicked){
                mTimePickerStart.show()
                btnSunday.setBackgroundResource(R.drawable.bottom_border)
            }else{
                btnSunday.setBackgroundResource(R.drawable.button_model)
            }
        }

        btnAll.setOnClickListener{
            isSelectAllClicked =! isSelectAllClicked
            if(isSelectAllClicked){
                mTimePickerStart.show()
                btnAll.setBackgroundResource(R.drawable.bottom_border)
                SelectAllDays()
            }else{
                btnAll.setBackgroundResource(R.drawable.button_model)
                DeselectAllDays()
            }
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

}
