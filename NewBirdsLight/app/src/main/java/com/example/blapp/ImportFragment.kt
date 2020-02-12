package com.example.blapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blapp.adapter.ImportAdapter
import com.example.blapp.databasehelper.DBmanager
import com.example.blapp.model.PgmItem
import kotlinx.android.synthetic.main.fragment_import.*
import kotlinx.android.synthetic.main.fragment_import_list.*
import kotlinx.android.synthetic.main.fragment_import_list.view.*


class ImportFragment : Fragment() {

    companion object{
        var isSelectedAll = false
    }
    var lstCheck = mutableListOf<String>()
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter : ImportAdapter
    internal lateinit var dbm:DBmanager
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_import, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbm = DBmanager(activity!!)
        navController = Navigation.findNavController(view)
        recycler_import.setHasFixedSize(true)
        recycler_import.setItemViewCacheSize(25)
        layoutManager = LinearLayoutManager(activity)
        recycler_import.layoutManager = layoutManager

        delete_import.setOnClickListener{
            for (item in adapter.itemList){
                if(item.isClicked){
                    lstCheck.add(item.name)
                }
            }
            if(lstCheck.isEmpty()){
                Toast.makeText(activity,"Select a program to delete!" , Toast.LENGTH_SHORT).show()
            }else{
                ShowDeleteAlert()
            }

        }
        select_all_checkbox.setOnCheckedChangeListener { _, isChecked ->
//                for (id in adapter.itemList) {
//                    adapter.context!!.import_checkbox.isChecked = true
//                }

            isSelectedAll = !isSelectedAll
            adapter.notifyDataSetChanged()
        }
        ShowImports()
    }

    private fun ShowImports(){
        adapter = ImportAdapter(activity, dbm.allpgm as MutableList<PgmItem>)
        recycler_import.adapter = adapter
    }
    private fun refreshList(){
        adapter.itemList.clear()
        ShowImports()
    }

    fun ShowDeleteAlert(){

        val mAlertDialog = AlertDialog.Builder(activity!!)
        mAlertDialog.setIcon(R.mipmap.ic_launcher_round)
        mAlertDialog.setTitle("Are you Sure you want to Delete?")


        mAlertDialog.setPositiveButton("Yes") { dialog, id ->

                for (itemname in lstCheck){

                    for(item in dbm.allpgm){
                        if(item.name == itemname){
                            dbm.deletePgm(itemname)
                        }
                    }
                    for(item in dbm.allStep){
                        if(item.pgm_name == itemname){
                            dbm.deleteStep(itemname)
                        }
                    }
                    for (item in dbm.allSched){
                        if(item.pgmname == itemname){
                            dbm.deleteSchedule(itemname)
                        }
                    }
                }
                select_all_checkbox.isChecked = false
                refreshList()

        }

        mAlertDialog.setNegativeButton("Cancel") { dialog, id ->
            lstCheck.clear()
        }

        mAlertDialog.show()
    }
}
