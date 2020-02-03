package com.example.blapp


import android.graphics.Color
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
import androidx.recyclerview.widget.RecyclerView
import com.CurrentId.extensions.CurrentID
import com.example.blapp.adapter.PgmAdapter
import com.example.blapp.collection.PgmCollection
import com.example.blapp.collection.StepCollection
import com.example.blapp.databasehelper.DBmanager
import com.example.blapp.helper.MyButton
import com.example.blapp.helper.MySwipeHelper
import com.example.blapp.helper.MySwipeHelper2
import com.example.blapp.listener.MyButtonClickListener
import com.example.blapp.model.PgmItem
import com.example.blapp.model.StepItem
import kotlinx.android.synthetic.main.fragment_input_dialog.*
import kotlinx.android.synthetic.main.fragment_input_dialog.view.*
import kotlinx.android.synthetic.main.fragment_program.*

class ProgramFragment : Fragment(){

    lateinit var navController: NavController
    lateinit var layoutManager: LinearLayoutManager

    lateinit var adapter: PgmAdapter

   // internal lateinit var dbStep:DBmanager
    internal lateinit var dbm:DBmanager
    internal var lstStep: List<StepItem> = ArrayList<StepItem>()
    internal var lstPgm: List<PgmItem> = ArrayList<PgmItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_program, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dbm = DBmanager(activity!!)

        recycler_pgm.setHasFixedSize(true)
        recycler_pgm.setItemViewCacheSize(25)
        layoutManager = LinearLayoutManager(activity)
        recycler_pgm.layoutManager = layoutManager

        //Add Swipe
        val swipe = object: MySwipeHelper(activity, recycler_pgm, 200)
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
                                DeleteAlert(pos)
                            }
                        }
                    )
                )

                buffer.add(
                    MyButton(activity,
                        "Update",
                        30,
                        R.drawable.ic_edit_dark_blue_24dp,
                        Color.parseColor("#14BED1"),
                        object : MyButtonClickListener{
                            override fun onClick(pos: Int) {
                                val bundle = bundleOf("parentPgmIndex" to  pos + 1)
                                navController.navigate(R.id.action_programFragment_to_setStepFragment, bundle )
                                CurrentID.UpdateID(num = 6)
                                CurrentID.Updatebool(x = true)
                            }
                        }
                    )
                )

                buffer.add(
                    MyButton(activity,
                        "Save",
                        30,
                        R.drawable.ic_save_dark_blue_24dp,
                        Color.parseColor("#14BED1"),
                        object : MyButtonClickListener{
                            override fun onClick(pos: Int) {
                                showCreateCategoryDialog(pos+1)
                            }
                        }
                    )
                )

                buffer.add(
                    MyButton(activity,
                        "Save",
                        30,
                        R.drawable.ic_date_range_dark_blue_24dp,
                        Color.parseColor("#14BED1"),
                        object : MyButtonClickListener{
                            override fun onClick(pos: Int) {
                                navController.navigate(R.id.action_programFragment_to_dayPicker)
                                CurrentID.UpdateID(num = 8)
                                CurrentID.Updatebool(x = true)
                            }
                        }
                    )
                )

                buffer.add(
                    MyButton(activity,
                        "Save",
                        30,
                        R.drawable.ic_save_dark_blue_24dp,
                        Color.parseColor("#14BED1"),
                        object : MyButtonClickListener{
                            override fun onClick(pos: Int) {
                                showCreateCategoryDialog(pos+1)
                            }
                        }
                    )
                )
            }
        }

        generateItem()
    }

    private fun generateItem() {
        adapter = PgmAdapter(activity, PgmCollection.pgmCollection)
        recycler_pgm.adapter = adapter
    }

    private fun SynchronizePgmCollection(){
        var pgmCollectionIndex = 1
        for(item in PgmCollection.pgmCollection)
        {
            item.pgm = pgmCollectionIndex.toByte()
            pgmCollectionIndex++
        }

        adapter = PgmAdapter(activity, PgmCollection.pgmCollection)
        recycler_pgm.adapter = adapter

//        itemList.clear()
//        for(item in PgmCollection.pgmCollection){
//            itemList.add(item)
//        }
//        adapter = PgmAdapter(activity, itemList)
//        recycler_pgm.adapter = adapter
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btn_new_pgm.setOnClickListener{
            var createdPgmIndex = 0
            if(PgmCollection.pgmCollection == null)
            {
                createdPgmIndex = 1
            }
            else
            {
                createdPgmIndex = PgmCollection.pgmCollection!!.count() + 1
            }

            val bundle = bundleOf("parentPgmIndex" to  createdPgmIndex)
            navController.navigate(R.id.action_programFragment_to_setStepFragment, bundle)
            CurrentID.UpdateID(num = 6)
            CurrentID.Updatebool(x = true)
        }
        btnImportFragment.setOnClickListener{
            navController.navigate(R.id.action_programFragment_to_importFragment)
            CurrentID.UpdateID(num = 9)
            CurrentID.Updatebool(x = true)
        }
    }
    private fun DeleteAlert(pos: Int){
        val mAlertDialog = AlertDialog.Builder(activity!!)
        mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
        mAlertDialog.setTitle("Title!") //set alertdialog title
        mAlertDialog.setMessage("Your message here") //set alertdialog message
        mAlertDialog.setPositiveButton("Yes") { dialog, id ->
            val pgmIndex = PgmCollection.pgmCollection.get(pos)
            RefreshStepCollection(pgmIndex.pgm!!.toInt())
            PgmCollection.pgmCollection.removeAt(pos)
            SynchronizePgmCollection()
            //Toast.makeText(activity!!, "Yes", Toast.LENGTH_SHORT).show()
        }
        mAlertDialog.setNegativeButton("No") { dialog, id ->
            Toast.makeText(activity!!, "No", Toast.LENGTH_SHORT).show()
        }
        mAlertDialog.show()
    }

    private fun SaveName(name: String){

    }

    private fun RefreshStepCollection(pgm: Int)
    {
        do{
            val retreivedStep = StepCollection.stepCollection.find { it.pgm!!.toInt() == pgm }
            StepCollection.stepCollection.remove(retreivedStep)

        }while (retreivedStep != null)

        for(item in StepCollection.stepCollection)
        {
            if(item.pgm!!.toInt() > pgm)
            {
                val newPgm = item.pgm!!.toInt() -1
                item.pgm = newPgm.toByte()
            }
        }
    }

    private fun AddPgmDb(pgm: Int){


    }

    fun showCreateCategoryDialog(pgm: Int) {

        val editAlert = AlertDialog.Builder(activity!!).create()

        val editView = layoutInflater.inflate(R.layout.fragment_input_dialog, null)

        editAlert.setView(editView)
        editAlert.show()

        editView.input_save.setOnClickListener{
            val textName = editAlert.name_input.text

            var pgmAdd = PgmCollection.pgmCollection.find { it.pgm == pgm.toByte() }
            var pgmNameCheck = dbm.allpgm.find { it.name == textName.toString() }



            if (pgmAdd != null) {
                when {
                    textName.isEmpty() -> {
                        editView.name_input.setBackgroundResource(R.drawable.redborder)
                        Toast.makeText(activity!!, "Please fill required field!" , Toast.LENGTH_LONG).show()
                    }
                    pgmNameCheck!= null -> {
                        editView.name_input.setBackgroundResource(R.drawable.redborder)
                        Toast.makeText(activity!!, "Name Already Taken!" , Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        pgmAdd.name = textName.toString()
                            var stepAdd = StepCollection.stepCollection.filter { it.pgm == pgm.toByte() }
                        for(steps in stepAdd){
                            steps.pgm_name = textName.toString()
                            dbm.addStep(steps)
                        }



                        dbm.addPgm(pgmAdd)
                        var test = dbm.allpgm
                        var test2 = dbm.allStep
                        editAlert.dismiss()
                        Toast.makeText(activity!!, "Save Success!" , Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        editView.input_cancel.setOnClickListener{
            editAlert.dismiss()
            Toast.makeText(activity!!, "Save Canceled!" , Toast.LENGTH_LONG).show()
        }
    }

}
