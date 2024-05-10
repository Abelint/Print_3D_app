package com.example.print_3d_app

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity


class CardListAdapter(private val context: Context, private val cardItems: List<CardItem>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return cardItems.size
    }

    override fun getItem(position: Int): Any {
        return cardItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = inflater.inflate(R.layout.card, parent, false)
        }

        // Настройка TextView, ImageButton и CheckBox
        val textView: TextView = view?.findViewById(R.id.tvName) ?: TextView(context)
        val imageButton1: ImageButton = view?.findViewById(R.id.ibWhatsap) ?: ImageButton(context)
        val imageButton2: ImageButton = view?.findViewById(R.id.ibTelegram) ?: ImageButton(context)
        val imageButton3: ImageButton = view?.findViewById(R.id.ibPhone)?: ImageButton(context)
        val checkBox1: CheckBox = view?.findViewById(R.id.chb_model)?: CheckBox(context)
        val checkBox2: CheckBox = view?.findViewById(R.id.chb_print)?: CheckBox(context)
        val checkBox3: CheckBox = view?.findViewById(R.id.chb_payment)?: CheckBox(context)



        // Установка данных или обработка нажатий для каждого элемента
        var currentItem = cardItems[position]
        imageButton1.setOnClickListener {

            val appName = "com.whatsapp"
            val isAppInstalled: Boolean =
                isAppAvailable(context.getApplicationContext(), appName)
            if (isAppInstalled) {
                val myIntent = Intent(Intent.ACTION_SEND)
                myIntent.setType("text/plain")
                myIntent.setPackage(appName)
                myIntent.putExtra(Intent.EXTRA_TEXT, "Здравствуйте\nИзделие готово\n3Д печать") //
                context.startActivity(Intent.createChooser(myIntent, "Share with"))
            } else {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Здравствуйте\nИзделие готово\n3Д печать")
                    type = "text/plain" //для URL можно использовать text/x-uri
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }
        }

        imageButton2.setOnClickListener {

            val appName = "org.telegram.messenger"
            val isAppInstalled: Boolean =
                isAppAvailable(context.getApplicationContext(), appName)
            if (isAppInstalled) {
                val myIntent = Intent(Intent.ACTION_SEND)
                myIntent.setType("text/plain")
                myIntent.setPackage(appName)
                myIntent.putExtra(Intent.EXTRA_TEXT, "Здравствуйте\nИзделие готово\n3Д печать") //
                context.startActivity(Intent.createChooser(myIntent, "Share with"))
            } else {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Здравствуйте\nИзделие готово\n3Д печать")
                    type = "text/plain" //для URL можно использовать text/x-uri
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }
        }

        imageButton3.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${currentItem.numberTelephone}")
            }
           context.startActivity(intent)


        }

        textView.text = currentItem.text
        checkBox1.isChecked = currentItem.checkBox1Value
        checkBox2.isChecked = currentItem.checkBox2Value
        checkBox3.isChecked = currentItem.checkBox3Value
        Log.i("currentItem checkBox1Value",currentItem.id + " " + currentItem.checkBox1Value.toString())

        checkBox1.setOnClickListener {
            val db = DBHelper(context, null)
            db.updateZapis(currentItem.id,"statusModeling",   (!cardItems[position].checkBox1Value).toString())
            db.close()
        }

        checkBox2.setOnClickListener {
            val db = DBHelper(context, null)
            db.updateZapis(currentItem.id,"statusPrinting", (!cardItems[position].checkBox2Value).toString())
            db.close()
        }
        checkBox3.setOnClickListener {
            val db = DBHelper(context, null)
            db.updateZapis(currentItem.id,"payment", (!cardItems[position].checkBox3Value).toString())
            db.close()
        }

        return view!!
    }
    fun isAppAvailable(context: Context, appName: String?): Boolean {
        val pm = context.packageManager
        return try {
            pm.getPackageInfo(appName!!, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: NameNotFoundException) {
            false
        }
    }

}