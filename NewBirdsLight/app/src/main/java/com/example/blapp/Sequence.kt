package com.example.blapp


import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.CurrentId.extensions.CurrentID
import kotlinx.android.synthetic.main.fragment_sequence.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Sequence : Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sequence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btn_Days_Range.setOnClickListener{
            navController.navigate(R.id.action_sequence_to_dayPicker)
            CurrentID.UpdateID(num = 8)
            CurrentID.Updatebool(x = true)
        }
        val mTimePickerStart: TimePickerDialog
        val mTimePickerEnd: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePickerStart = TimePickerDialog(activity, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                txtTimeFrom.setText(String.format("From: %d : %d", hourOfDay, minute))
                btn_End_Time.isEnabled = true
            }

        }, hour, minute, false)

        mTimePickerEnd = TimePickerDialog(activity, object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?,hourOfDay: Int, minute: Int) {
                txtTimeTo.setText(String.format("To: %d : %d", hourOfDay, minute))
            }
        }, hour , minute , false)

        btn_Start_Time.setOnClickListener{
            mTimePickerStart.show()}

        btn_End_Time.setOnClickListener{
            mTimePickerEnd.show()
        }
    }

}
