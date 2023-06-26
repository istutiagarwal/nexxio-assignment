package com.example.nexxio.data

import android.net.Uri


data class NexxioData(
    val type : String,
    var id : String,
    val title : String,
    val dataMap : Map<String,List<String>>,
    var imagePath : Uri?,
    var res : String
){}


