package com.example.print_3d_app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InWork.newInstance] factory method to
 * create an instance of this fragment.
 */
class InWork : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var listView: ListView
    private lateinit var cardItems: MutableList<CardItem>
    private lateinit var cardAdapter: CardListAdapter

    private var listener: FragmentInteractionListener? = null

    private var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_in_work, container, false)

        listView = view.findViewById(R.id.lvCards)
        cardItems = mutableListOf()
        cardAdapter = CardListAdapter(requireContext(), cardItems)
        listView.adapter = cardAdapter


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewCardItem()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentInteractionListener")
        }
    }


    fun addNewCardItem() {
        val newText = "Item ${cardItems.size + 1}"
        val newCheckBox1Value = (0..1).random() == 1
        val newCheckBox2Value = (0..1).random() == 1
        val newCheckBox3Value = (0..1).random() == 1

        val newCardItem = CardItem(newText, newCheckBox1Value, newCheckBox2Value, newCheckBox3Value)
        cardItems.add(newCardItem)
        cardAdapter.notifyDataSetChanged()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InWork.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InWork().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}