package com.example.blapp.adapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_time_list.view.*

class TimeViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    var txt_num : TextView
    var txt_title : TextView
    init{
        txt_num = itemView.time_num
        txt_title = itemView.time_title
    }

}