package com.example.print_3d_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ArchiveLineListAdapter (private val context: Context, private val archiveLines: List<ArchiveLineItem>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return archiveLines.size
    }

    override fun getItem(position: Int): Any {
        return archiveLines[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = inflater.inflate(R.layout.card, parent, false)
        }

        // Настройка TextViews
        val tvArchivOrderName: TextView = view?.findViewById(R.id.tvArchivOrderName) ?: TextView(context)
        val tvArchivDate: TextView = view?.findViewById(R.id.tvArchivDate) ?: TextView(context)
        val currentItem = archiveLines[position]
        tvArchivOrderName.text = currentItem.nameOrder
        tvArchivDate.text = currentItem.date

        return view!!
    }


}