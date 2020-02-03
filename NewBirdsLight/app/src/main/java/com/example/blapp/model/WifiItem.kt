package com.example.blapp.model

class WifiItem{
    var name: String? = ""
    var status: Boolean = false
    var level: Int? = 0
    var selected: Boolean = false
    var capabilities: String? = ""

        //Getter
        get() = field
        //Setter
        set(value){
            field = value
        }
}