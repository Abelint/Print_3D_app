package com.example.print_3d_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), FragmentInteractionListener {
    lateinit var fragInWork: InWork
    lateinit var fragArchive: Fragment
    lateinit var tvTest :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTest = findViewById(R.id.textView2)

        val addButton: ImageButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            tvTest.text = "aegvaegvaergvaer"
            addNewCardItem()
        }

    }

    override fun addNewCardItem() {

        // Получите ссылку на фрагмент
       val yourFragment = supportFragmentManager.findFragmentById(R.id.kfragment_in_work) as InWork
        // Вызовите функцию addNewCardItem() из фрагмента
        yourFragment.addNewCardItem()
    }


}