package com.example.print_3d_app

 public  class ConstantsDB {
     companion object {
         val DB_NAME = "print2.db"
         val DB_VER =1

         val TABLE_NAME = "users1"
         val _ID = "_id"
         val TITLE = "title"
         val DESCRIPTION = "description"

         val columnList : List<String> = listOf(
             "numberTelephone","nameMan", "nameModel",  "chWhatsapp", "chTelegram"
             ,  "chPhone", "urlModel",  "priceModeling", "pricePrinting"
             , "avans", "statusModeling", "statusPrinting", "payment"
         )

         public fun CreateTableQuery(tableName : String, list: List<String>) : String {
             var query = "CREATE TABLE IF NOT EXISTS " + tableName+
                     " (" + _ID + " INTEGER PRIMARY KEY"
             for(s in list){
                 query += ", "+ s + " TEXT"
             }
             query += ")"
             return  query
         }
     }


}