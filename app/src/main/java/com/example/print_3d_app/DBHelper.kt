package com.example.print_3d_app

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

     override fun onCreate(db: SQLiteDatabase) {


        var  query = ConstantsDB.CreateTableQuery(TABLE_NAME, ConstantsDB.columnList)
         /*
        query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                AGE_COL + " TEXT" + ")")

          */

        Log.i("query", query)
        // we are calling sqlite
        // method for executing our query

        db.execSQL(query)

       // db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }


    fun addZapis(stroka : ZapisInDB ){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        values.put(ConstantsDB.columnList[0], stroka.numberTelephone)
        values.put(ConstantsDB.columnList[1], stroka.nameMan)
        values.put(ConstantsDB.columnList[2], stroka.nameModel)
        values.put(ConstantsDB.columnList[3], stroka.chWhatsapp)
        values.put(ConstantsDB.columnList[4], stroka.chTelegram)
        values.put(ConstantsDB.columnList[5], stroka.chPhone)
        values.put(ConstantsDB.columnList[6], stroka.urlModel)
        values.put(ConstantsDB.columnList[7], stroka.priceModeling)
        values.put(ConstantsDB.columnList[8], stroka.pricePrinting)
        values.put(ConstantsDB.columnList[9], stroka.avans)
        values.put(ConstantsDB.columnList[10], stroka.statusModeling)
        values.put(ConstantsDB.columnList[11], stroka.statusPrinting)
        values.put(ConstantsDB.columnList[12], stroka.payment)

        val db = this.writableDatabase

        Log.i("query", TABLE_NAME + " TN " + values.toString())
        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }


    fun addZapisQuery(stroka : ZapisInDB ){

        var sqLQuery = "INSERT INTO " + TABLE_NAME + " ("
        for (str in ConstantsDB.columnList){
            sqLQuery += str + ", "
        }
        sqLQuery = sqLQuery.substring(0,sqLQuery.length-2)
        sqLQuery += ") VALUES ("
        sqLQuery += stroka.numberTelephone + ", "
        sqLQuery += stroka.nameMan + ", "
        sqLQuery += stroka.nameModel + ", "
        sqLQuery += stroka.chWhatsapp.toString() + ", "
        sqLQuery += stroka.chTelegram.toString() + ", "
        sqLQuery += stroka.chPhone.toString() + ", "
        sqLQuery += stroka.urlModel + ", "
        sqLQuery += stroka.priceModeling + ", "
        sqLQuery += stroka.pricePrinting + ", "
        sqLQuery += stroka.avans + ", "
        sqLQuery += stroka.statusModeling.toString() + ", "
        sqLQuery += stroka.statusPrinting.toString() + ", "
        sqLQuery += stroka.payment.toString() + ")"

        Log.i("query __ sqLQuery", sqLQuery)

        val db = this.writableDatabase

        db.execSQL(sqLQuery)

        // at last we are
        // closing our database
        db.close()
    }
    // below method is to get
    // all data from our database
    fun getName(): Cursor? {

        val db = this.readableDatabase
        Log.i("btnInWork","zdes " + "ShowActuallyOrders getName " + TABLE_NAME )
        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    fun updateZapis(id: String, zapis: ZapisInDB){
        val db = this.writableDatabase

        val contentValues = ContentValues().apply {

            put(ConstantsDB.columnList[0], zapis.numberTelephone)
            put(ConstantsDB.columnList[1], zapis.nameMan)
            put(ConstantsDB.columnList[2], zapis.nameModel)
            put(ConstantsDB.columnList[3], zapis.chWhatsapp)
            put(ConstantsDB.columnList[4], zapis.chTelegram )
            put(ConstantsDB.columnList[5], zapis.chPhone)
            put(ConstantsDB.columnList[6], zapis.urlModel)
            put(ConstantsDB.columnList[7], zapis.priceModeling)
            put(ConstantsDB.columnList[8], zapis.pricePrinting)
            put(ConstantsDB.columnList[9], zapis.avans)
            put(ConstantsDB.columnList[10], zapis.statusModeling)
            put(ConstantsDB.columnList[11], zapis.statusPrinting)
            put(ConstantsDB.columnList[12], zapis.payment)
            put(ConstantsDB.columnList[13], zapis.date)
        }

        val selection = "rowid = ?"
        val selectionArgs = arrayOf(id.toString())

        val rowsUpdated = db.update(TABLE_NAME, contentValues, selection, selectionArgs)

        if (rowsUpdated > 0) {
            Log.i("updateZapis", "Значение успешно обновлено для строки $id")
        } else {
            Log.i("updateZapis","Не удалось обновить значение для строки $id")
        }
        db.close()
    }
    fun updateZapis(id: String, columnName: String, value : String) {
        val contentValues = ContentValues().apply {
            put(columnName, value)
        }
        val db = this.writableDatabase
        db.update(ConstantsDB.TABLE_NAME, contentValues, ConstantsDB._ID +" = ?", arrayOf(id.toString())) // Замените на имя вашей таблицы и нужное условие
        db.close()
    }

    @SuppressLint("Range")
    fun getAllZapis() : List<ZapisInDB>{
        val list = mutableListOf<ZapisInDB>()
        val cursor = this.getName()

        var fff =""
        cursor!!.moveToFirst()
        // fff+=cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[0])) + "\n"
        // fff+=cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[1])) + "\n"

        // moving our cursor to next
        // position and appending values
        while(cursor.moveToNext()){
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
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[12])).toBoolean(),
                cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[13])))
            list.add(zap)
        }

        // at last we close our cursor
        cursor.close()
        return list
    }



    @SuppressLint("Range")
    fun gatZapisById(id : Int) : ZapisInDB {

Log.i("gatZapisById", id.toString())
        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_NAME, // Замените на имя вашей таблицы
            null,
            ConstantsDB._ID+" = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        var result: String? = null

        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex("numberTelephone")) // Замените на имя нужного столбца
        }

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
            cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[12])).toBoolean(),
            cursor.getString(cursor.getColumnIndex(ConstantsDB.columnList[13])))

        cursor.close()
        db.close()

        return zap
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = ConstantsDB.DB_NAME

        // below is the variable for database version
        private val DATABASE_VERSION = ConstantsDB.DB_VER

        // below is the variable for table name
        val TABLE_NAME = ConstantsDB.TABLE_NAME



    }
}