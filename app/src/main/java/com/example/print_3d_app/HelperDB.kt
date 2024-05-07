package com.example.print_3d_app

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SampleDatabase.db"
        private const val TABLE_NAME = "SampleTable"
        private const val COLUMN_ID = "ID"
        private const val COLUMN_NAME = "Name"
    }

    @Override
    override fun onCreate(db: SQLiteDatabase?) {

        if (db != null) db.execSQL(ConstantsDB.CreateTableQuery(ConstantsDB.TABLE_NAME, ConstantsDB.columnList))

    }

    @Override
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(name: ZapisInDB) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ConstantsDB.columnList[0], name.numberTelephone)
        values.put(ConstantsDB.columnList[1], name.nameMan)
        values.put(ConstantsDB.columnList[2], name.nameModel)
        values.put(ConstantsDB.columnList[3], name.chWhatsapp)
        values.put(ConstantsDB.columnList[4], name.chTelegram)
        values.put(ConstantsDB.columnList[5], name.chPhone)
        values.put(ConstantsDB.columnList[6], name.urlModel)
        values.put(ConstantsDB.columnList[7], name.priceModeling)
        values.put(ConstantsDB.columnList[8], name.pricePrinting)
        values.put(ConstantsDB.columnList[9], name.avans)
        values.put(ConstantsDB.columnList[10], name.statusModeling)
        values.put(ConstantsDB.columnList[11], name.statusPrinting)
        values.put(ConstantsDB.columnList[12], name.payment)
       // "numberTelephone","nameMan", "nameModel",  "chWhatsapp", "chTelegram"
       // ,  "chPhone", "urlModel",  "priceModeling", "pricePrinting"
       // , "avans", "statusModeling", "statusPrinting", "payment"
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    @SuppressLint("Range")
    fun readData(): List<String> {
        val dataList = mutableListOf<String>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                dataList.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return dataList
    }
}