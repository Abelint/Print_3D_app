package com.example.print_3d_app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

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

    private var notFirstStart :Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.i("btnInWork","zdes " + "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_in_work, container, false)

        Log.i("btnInWork","zdes " + "onCreateView")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.lvCards)
        cardItems = mutableListOf()
        cardAdapter = CardListAdapter(requireContext(), cardItems)
        listView.adapter = cardAdapter
        if(cardAdapter != null) {
            Log.i("btnInWork","zdes " + "onViewCreated " +
                    cardAdapter.toString() + "   "+ cardItems.count())}
        Log.i("btnInWork","zdes " + "onViewCreated " )
    }

    override fun onResume() {

        super.onResume()
        if(cardAdapter != null) {
            Log.i("btnInWork","zdes " + "onViewCreated " +
                    cardAdapter.toString() + "   "+ cardItems.count())}

        Log.i("btnInWork","zdes " + "onResume")
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(notFirstStart) return
        if (context is FragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentInteractionListener")
        }


        Log.i("btnInWork","zdes " + "onAttach")
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("btnInWork","zdes " + "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.i("btnInWork","zdes " + "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.i("btnInWork","zdes " + "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i("btnInWork","zdes " + "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("btnInWork","zdes " + "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("btnInWork","zdes " + "onDestroy")
    }
    override fun onDetach() {
        super.onDetach()
        Log.i("btnInWork","zdes " +"onDetach")
    }
    fun clearCards(){
        cardItems.clear()
        cardAdapter.notifyDataSetChanged()
    }

    @SuppressLint("Range")
    fun reloadCards(){
        Log.i("btnInWork","zdes " + "ShowActuallyOrders " )

      clearCards()

        val db = DBHelper(requireContext(), null)
        val cursor = db.getName()

        var fff =""
        cursor!!.moveToFirst()
        // fff+=cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[0])) + "\n"
        // fff+=cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[1])) + "\n"

        // moving our cursor to next
        // position and appending values
        while(cursor.moveToNext()){
            Log.i("btnInWork","zdes " + "ShowActuallyOrders "  + ConstantsDB.columnList[0])
            val zap = ZapisInDB( cursor.getString(0) ,
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[0])),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[1])),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[2])),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[3])).toBoolean(),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[4])).toBoolean(),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[5])).toBoolean(),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[6])),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[7])),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[8])),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[9])),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[10])).toBoolean(),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[11])).toBoolean(),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[12])).toBoolean())

            addNewCardItem(zap)
        }

        // at last we close our cursor
        cursor.close()

        Log.i("query fff", fff)


    }

    fun addNewCardItem(card : ZapisInDB) {
        Log.i("btnInWork","zdes " +"addNewCardItem")

        val newText =  if(card.nameModel!= null) card.nameModel else "noName"
        val newCheckBox1Value =  if(card.statusModeling!= null) card.statusModeling else false
        val newCheckBox2Value = if(card.statusPrinting!= null) card.statusPrinting  else false
        val newCheckBox3Value =  if(card.payment!= null) card.payment  else false
        val newTelep =  if(card.numberTelephone!= null) card.numberTelephone else ""
        if(newCheckBox1Value && newCheckBox2Value && newCheckBox3Value) return
        val newCardItem = CardItem(card.id,newText,newTelep, newCheckBox1Value, newCheckBox2Value, newCheckBox3Value)
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