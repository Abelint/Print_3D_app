package com.example.print_3d_app

import kotlinx.serialization.Serializable

@Serializable
public class ZapisInDB(val id: String, val numberTelephone: String?,
                      val nameMan: String? = null, val nameModel: String? = null, val chWhatsapp: Boolean
                      ,val chTelegram:Boolean, val chPhone:Boolean,val urlModel: String? = null
                      , val priceModeling: String? = null, val pricePrinting: String? = null
                      , val avans: String? = null
                      , val statusModeling: Boolean? = null, val statusPrinting: Boolean? = null
                      , val payment: Boolean? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.


}

/* пример экземпляра класса
  val zap = ZapisInDB( "numberTelephone","nameMan", "nameModel",  true, false
                ,  true, "urlModel",  "priceModeling", "pricePrinting"
                , "avans", true, false, false)

 */