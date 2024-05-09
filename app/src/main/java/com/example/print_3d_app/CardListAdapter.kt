package com.example.print_3d_app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
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
}