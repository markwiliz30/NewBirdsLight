package com.example.blapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.CurrentId.extensions.CurrentID
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnManageSequence.setOnClickListener{
            navController.navigate(R.id.action_scheduleFragment_to_sequence)
            CurrentID.UpdateID(num = 7)
            CurrentID.Updatebool(x = true)
        }
        btnDateRange.setOnClickListener{
            navController.navigate(R.id.action_scheduleFragment_to_calendarFragment)
            CurrentID.UpdateID(num = 10)
            CurrentID.Updatebool(x = true)
        }
    }

}
