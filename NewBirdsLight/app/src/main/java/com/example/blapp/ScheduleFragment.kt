package com.example.blapp


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blapp.adapter.ScheduleAdapter
import com.example.blapp.collection.ScheduleCollection
import com.example.blapp.helper.MyButton
import com.example.blapp.helper.MySwipeHelper
import com.example.blapp.helper.MySwipeHelper2
import com.example.blapp.listener.MyButtonClickListener
import com.example.blapp.model.ScheduleItem
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var adapter: ScheduleAdapter
    lateinit var layoutManager: LinearLayoutManager
    private lateinit var dialog: AlertDialog
    var scheduleList : MutableList<ScheduleItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog = SpotsDialog(activity, R.style.Custom)
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        FilterUniqueItem()
        adapter = ScheduleAdapter(activity, scheduleList)
        lst_created_pgm.adapter = adapter

//        var datePicker: PrimeDatePickerBottomSheet? = null
//        this.calendar_event_view.onDayPickedListener
//        calendar_event_view.calendarType = CalendarType.CIVIL
//        calendar_event_view.flingOrientation = PrimeCalendarView.FlingOrientation.HORIZONTAL
//        PrimeCalendarView
//
//        btnDateRange.setOnClickListener{
//
//            val calendarType = CalendarType.CIVIL
//
//            val pickType = PickType.RANGE_START
//
//            val minDateCalendar= null
//
//            val maxDateCalendar = null
//
//            val typeface = null
//
//            val today = CalendarFactory.newInstance(calendarType)
//
//
//            datePicker = PrimeDatePickerBottomSheet.newInstance(
//                currentDateCalendar = today,
//                minDateCalendar = minDateCalendar,
//                maxDateCalendar = maxDateCalendar,
//                pickType = pickType,
//                typefacePath = typeface
//            )
//            datePicker?.setOnDateSetListener(this)
//            datePicker?.show(activity!!.supportFragmentManager, "PrimeDatePickerBottomSheet")
//        }

        btn_upload_all.setOnClickListener{
            //dialog.show()

            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            val view: View = layoutInflater.inflate(R.layout.layout_loading_dialog, null)

            builder.setView(view).setNegativeButton("Cancel") { dialog, which ->
            }

            val dialog = builder.create()
            dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#ffffff")) }
            dialog.show()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lst_created_pgm.setHasFixedSize(true)
        lst_created_pgm.setItemViewCacheSize(25)
        layoutManager = LinearLayoutManager(activity)
        lst_created_pgm.layoutManager = layoutManager

        val swipe = object: MySwipeHelper2(activity, lst_created_pgm, 200)
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
                        object : MyButtonClickListener {
                            override fun onClick(pos: Int) {
                                Toast.makeText(activity, "wew"+pos, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                )
            }
        }
    }

    fun FilterUniqueItem(){
        var tempSchedItem: ScheduleItem? = null
        for(item in ScheduleCollection.scheduleCollection) {
            if(tempSchedItem != null)
            {
                if(!(tempSchedItem!!.smonth == item.smonth && tempSchedItem.sday == item.sday && tempSchedItem.emonth == item.emonth && tempSchedItem.eday == tempSchedItem.eday))
                {
                    tempSchedItem = item
                    scheduleList.add(tempSchedItem)
                }
            }
            else
            {
                tempSchedItem = item
                scheduleList.add(tempSchedItem)
            }
        }
    }
}
