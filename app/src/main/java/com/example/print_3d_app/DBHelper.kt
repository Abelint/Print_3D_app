package com.example.print_3d_app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

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

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "GEEKS_FOR_GEEKS1.db"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "print"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val NAME_COl = "name"

        // below is the variable for age column
        val AGE_COL = "age"
    }
}