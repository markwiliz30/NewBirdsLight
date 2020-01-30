package com.example.blapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.CurrentId.extensions.CurrentID
import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.common.CalendarFactory
import com.aminography.primecalendar.common.CalendarType
import com.aminography.primedatepicker.PickType
import com.aminography.primedatepicker.calendarview.PrimeCalendarView
import com.aminography.primedatepicker.fragment.PrimeDatePickerBottomSheet
import com.aminography.primedatepicker.tools.screenSize
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.util.*

class ScheduleFragment : Fragment() ,PrimeDatePickerBottomSheet.OnDayPickedListener {

    lateinit var navController: NavController
    private var datePicker: PrimeDatePickerBottomSheet? = null
    private var calendarType = CalendarType.CIVIL

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
        this.calendar_event_view.onDayPickedListener
        calendar_event_view.calendarType = calendarType
        calendar_event_view.flingOrientation = PrimeCalendarView.FlingOrientation.HORIZONTAL
        PrimeCalendarView

        btnDateRange.setOnClickListener{

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

    override fun onMultipleDaysPicked(multipleDays: List<PrimeCalendar>) {

    }

    override fun onRangeDaysPicked(startDay: PrimeCalendar, endDay: PrimeCalendar) {

    }

    override fun onSingleDayPicked(singleDay: PrimeCalendar) {

    }

}
