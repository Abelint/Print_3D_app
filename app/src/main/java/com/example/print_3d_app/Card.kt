package com.example.print_3d_app

import android.content.Context
import android.util.AttributeSet
import android.view.View

data class CardItem(
    val id:String,
    var text: String,
    val numberTelephone : String,
    var checkBox1Value: Boolean,
    val checkBox2Value: Boolean,
    val checkBox3Value: Boolean

)