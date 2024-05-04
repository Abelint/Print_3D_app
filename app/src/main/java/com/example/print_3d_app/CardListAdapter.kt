package com.example.print_3d_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView

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
        val imageButton3: ImageButton = view?.findViewById(R.id.ibTelegram)?: ImageButton(context)
        val checkBox1: CheckBox = view?.findViewById(R.id.chb_model)?: CheckBox(context)
        val checkBox2: CheckBox = view?.findViewById(R.id.chb_print)?: CheckBox(context)
        val checkBox3: CheckBox = view?.findViewById(R.id.chb_payment)?: CheckBox(context)

        // Установка данных или обработка нажатий для каждого элемента
        val currentItem = cardItems[position]
        textView.text = currentItem.text
        checkBox1.isChecked = currentItem.checkBox1Value
        checkBox2.isChecked = currentItem.checkBox2Value
        checkBox3.isChecked = currentItem.checkBox3Value

        return view!!
    }
}