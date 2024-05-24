package com.example.print_3d_app

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import java.util.UUID
import kotlin.random.Random

class MainActivity : AppCompatActivity(), FragmentInteractionListener, CardListAdapter.CardItemLongClickListener {
    lateinit var addButton: ImageButton
    private lateinit var database: DatabaseReference
    private lateinit var fragmentInWork: InWork
    private var listOfOrders: MutableList<ZapisInDB> = mutableListOf()


    suspend fun getMessage() : String{
        delay(500L)  // имитация продолжительной работы
        return "Hello"
    }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction().replace(R.id.frameForFragmentInWork, InWork()).addToBackStack(null).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frameForFragmentArchive, Archiv()).addToBackStack(null).commit()
        findViewById<FrameLayout>(R.id.frameForFragmentArchive).visibility = View.INVISIBLE

        val btnInWork: Button = findViewById(R.id.btn_inwork)
        val btnArchive: Button = findViewById(R.id.btn_archive)


        btnInWork.setOnClickListener {


            addButton.visibility = View.VISIBLE
           // supportFragmentManager.beginTransaction().remove(InWork()).commitNow()
            /*
            supportFragmentManager.beginTransaction().replace(R.id.frameForFragment, fragmentInWork)
                .addToBackStack(null)
                .commit()
*/
            findViewById<FrameLayout>(R.id.frameForFragmentInWork).visibility = View.VISIBLE
            findViewById<FrameLayout>(R.id.frameForFragmentArchive).visibility = View.INVISIBLE

        }

        btnArchive.setOnClickListener {

            addButton.visibility = View.INVISIBLE
            findViewById<FrameLayout>(R.id.frameForFragmentInWork).visibility = View.INVISIBLE
            findViewById<FrameLayout>(R.id.frameForFragmentArchive).visibility = View.VISIBLE
/*
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameForFragment, Archiv())
            transaction.addToBackStack("Archiv")
            transaction.commit()

*/
        }




        addButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {

            showDialog()
            // database = Firebase.database.reference
            // writeFirebase(database)
            // readFirebase(database)

        }
    }


    override fun onResume() {
        super.onResume()

        ShowActuallyOrders()

    }

    fun ShowActuallyOrders(){

        Log.i("btnInWork","zdes " + "ShowActuallyOrders " )

        listOfOrders.clear()
        // Получите ссылку на фрагмент
        fragmentInWork = supportFragmentManager.findFragmentById(R.id.frameForFragmentInWork) as InWork
        // Вызовите функцию addNewCardItem() из фрагмента
        fragmentInWork.clearCards()

        val db = DBHelper(this, null)

          for(zap in db.getAllZapis()){
              listOfOrders.add(zap)
              addNewCardItem(zap)
          }
    }

    override fun addNewCardItem(card : ZapisInDB) {

        // Получите ссылку на фрагмент
        fragmentInWork = supportFragmentManager.findFragmentById(R.id.frameForFragmentInWork) as InWork
        // Вызовите функцию addNewCardItem() из фрагмента
        fragmentInWork.addNewCardItem(card)


    }

    fun showDialog() {
        val dialog: Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_new_order)

        val butSave = dialog.findViewById<Button>(R.id.buttonSave)
        val butCancel = dialog.findViewById<Button>(R.id.buttonCancel)






        butSave.setOnClickListener {
            val telephoneNumber = dialog.findViewById<EditText>(R.id.etPhone)
            val order = dialog.findViewById<EditText>(R.id.etOrder)
            val nameZakazchik = dialog.findViewById<EditText>(R.id.etName)
            val cbWhatsApp = dialog.findViewById<CheckBox>(R.id.cbWhatsapp)
            val cbTelegram = dialog.findViewById<CheckBox>(R.id.cbTelegram)
            val cbPhone = dialog.findViewById<CheckBox>(R.id.cbPhone)
            val etPriceModel = dialog.findViewById<EditText>(R.id.etPriceModel)
            val etPricePrint = dialog.findViewById<EditText>(R.id.etPricePrint)
            val etAvans = dialog.findViewById<EditText>(R.id.etAvans)


            val newOrder = ZapisInDB("-1",
                telephoneNumber.text.toString(),
                nameZakazchik.text.toString(),
                order.text.toString(),
                cbWhatsApp.isChecked,
                cbTelegram.isChecked,
                cbPhone.isChecked,
                "",
                etPriceModel.text.toString(),
                etPricePrint.text.toString(),
                etAvans.text.toString(),
                false,
                false,
                false
            )

            val db = DBHelper(this, null)

            db.addZapis(newOrder)
            db.close()
            //  db.addZapisQuery(zap)
            // Toast to message on the screen
            Toast.makeText(this, "Запись добавлена", Toast.LENGTH_LONG).show()
            Thread.sleep(50)

            ShowActuallyOrders()

           // sendToDB(newOrder)
            // getFromDB()
             dialog.dismiss()

        }
        dialog.show()
    }


    // нужно вывести работу с  firebase в отдельный поток или через callback
    fun sendToDB(user: ZapisInDB) {  // работает хорошо
        //настройка подключения к firebase
        // https://firebase.google.com/docs/android/setup?hl=ru#console
        database = Firebase.database.reference

        if (user.numberTelephone != null) database.child("users").child(user.numberTelephone)
            .setValue(user)


    }

    suspend fun getFromDB(): MutableList<ZapisInDB> {
        database = Firebase.database.reference
        var listFromDB: MutableList<ZapisInDB> = mutableListOf()
        database.child("users").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            val children = it.getChildren()
            for (child in children) {
                //  val user = child.getValue(ZapisInDB::class.java) // с нул не канает
                Log.d("FireBase", "Value is: key ${child.key} val ${child.value}")
                val mass = child.value.toString()
                    .replace("{", "")
                    .replace("}", "")
                    .split(',')
                val zapis = mutableMapOf("" to "")
                for (g in mass) {
                    Log.d("mass", "Value G is:${g} ")
                    zapis.put(g.split('=')[0], g.split('=')[1])
                    Log.d("mass", g.split('=')[0]+","+ g.split('=')[1])
                }
                var numberTelephone: String? =
                    if (zapis.containsKey("numberTelephone")) zapis.get("numberTelephone")
                    else null
                var nameMan: String? =
                if (zapis.containsKey("nameMan")) zapis.get("nameMan")
                else null
                var nameModel: String? =
                    if (zapis.containsKey("nameModel")) zapis.get("nameModel")
                    else null
                var chWhatsapp: Boolean =
                    if (zapis.containsKey("chWhatsapp")) zapis.get("chWhatsapp").toBoolean()
                    else false
                var chTelegram:Boolean =
                    if (zapis.containsKey("chTelegram")) zapis.get("chTelegram").toBoolean()
                    else false
                var chPhone:Boolean =
                    if (zapis.containsKey("chPhone")) zapis.get("chPhone").toBoolean()
                    else false
                var urlModel: String? =
                    if (zapis.containsKey("urlModel")) zapis.get("urlModel")
                    else null
                var priceModeling: String? =
                    if (zapis.containsKey("priceModeling")) zapis.get("priceModeling")
                    else null
                var pricePrinting: String? =
                    if (zapis.containsKey("pricePrinting")) zapis.get("pricePrinting")
                    else null
                var avans: String? =
                    if (zapis.containsKey("avans")) zapis.get("avans")
                    else null
                var statusModeling: Boolean? =
                    if (zapis.containsKey("statusModeling")) zapis.get("statusModeling").toBoolean()
                    else false
                var statusPrinting: Boolean? =
                    if (zapis.containsKey("statusPrinting")) zapis.get("statusPrinting").toBoolean()
                    else false
                var payment: Boolean? =
                    if (zapis.containsKey("payment")) zapis.get("payment").toBoolean()
                    else false

                //val zapisFromDB = ZapisInDB(numberTelephone,nameMan,nameModel,chWhatsapp,chTelegram, chPhone, urlModel,priceModeling,pricePrinting,avans,statusModeling,statusPrinting,payment)
               // listFromDB.add(zapisFromDB)
                //listOfOrders.add(zapisFromDB)
                //addNewCardItem(zapisFromDB)
                Log.i("listFromDB", "Add value ${listFromDB.count()}")

            }

        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
        Log.i("listFromDB", "Got value ${listFromDB.count()}")
        return listFromDB
    }

    override fun onCardItemLongClicked(position: Int, message: String) {
        // здесь показывать заполненный диалог
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

}

