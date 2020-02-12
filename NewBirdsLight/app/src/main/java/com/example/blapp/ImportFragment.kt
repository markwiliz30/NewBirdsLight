package com.example.blapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    lateinit var layoutManager: LinearLayoutManager

    lateinit var adapter : ImportAdapter
    internal lateinit var dbm:DBmanager
    internal var lstChecked: List<PgmItem> = ArrayList<PgmItem>()
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_import, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dbm = DBmanager(activity!!)

        recycler_import.setHasFixedSize(true)
        recycler_import.setItemViewCacheSize(25)
        layoutManager = LinearLayoutManager(activity)
        recycler_import.layoutManager = layoutManager
        ShowImports()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        delete_import.setOnClickListener{
            for (item in adapter.itemList){
                if(item.isClicked){

                }
            }
        }
        select_all_checkbox.setOnCheckedChangeListener { _, isChecked ->
//                for (id in adapter.itemList) {
//                    adapter.context!!.import_checkbox.isChecked = true
//                }

            isSelectedAll = !isSelectedAll
            adapter.notifyDataSetChanged()
        }
    }

    private fun ShowImports(){
        adapter = ImportAdapter(activity, dbm.allpgm)
        recycler_import.adapter = adapter
    }
}
