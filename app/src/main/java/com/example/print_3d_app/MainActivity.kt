package com.example.print_3d_app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), FragmentInteractionListener {
    lateinit var addButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.frameForFragment, InWork()).commit()

        val btnInWork: Button = findViewById(R.id.btn_inwork)
        btnInWork.setOnClickListener {
            addButton.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.frameForFragment, InWork()).commit()
        }
        val btnArchive: Button = findViewById(R.id.btn_archive)
        btnArchive.setOnClickListener {
            addButton.visibility = View.INVISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.frameForFragment, Archiv()).commit()
        }

        addButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            sendToDB()
           // showDialog();
           // addNewCardItem()
        }




    }

    override fun addNewCardItem() {

        // Получите ссылку на фрагмент
       val yourFragment = supportFragmentManager.findFragmentById(R.id.frameForFragment) as InWork
        // Вызовите функцию addNewCardItem() из фрагмента
        yourFragment.addNewCardItem()
    }

    fun showDialog(){
        val dialog : Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_new_order)

        ///////////////////////////////////////////////////////////////////////////добавить логику

        dialog.show()
    }

    fun sendToDB(){
        val database = FirebaseDatabase.getInstance()
        val reference=database.getReference("order")
        val userText = "Ou May"
        val userid = reference.push().key
        val userReference = reference.child(userid.toString())

        userReference.setValue(userText).addOnSuccessListener {
            Toast.makeText(this, "Добавлено", Toast.LENGTH_LONG).show()
        }
    }

}