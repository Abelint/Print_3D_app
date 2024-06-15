package com.example.print_3d_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView


/**
 * A simple [Fragment] subclass.
 * Use the [Archiv.newInstance] factory method to
 * create an instance of this fragment.
 */
class Archiv : Fragment() {
   private lateinit var listView : ListView
    private lateinit var archiveLineItems: MutableList<ArchiveLineItem>
    private lateinit var archiveLineAdapter: ArchiveLineListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_archiv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Archiv.
         */
        // TODO: Rename and change types and number of parameters

    }
}