package com.example.print_3d_app

import kotlinx.serialization.Serializable

@Serializable
public class ZapisInDB(val id: String, val numberTelephone: String?,
                      val nameMan: String? = null, val nameModel: String? = null, val chWhatsapp: Boolean
                      ,val chTelegram:Boolean, val chPhone:Boolean,val urlModel: String? = null
                      , val priceModeling: String? = null, val pricePrinting: String? = null
                      , val avans: String? = null
                      , val statusModeling: Boolean? = null, val statusPrinting: Boolean? = null
                      , val payment: Boolean? = null, val date: String?= null) {
    /*
    val ID get() = id
    val NumberTelephone get() = numberTelephone
    val NameMan get() = nameMan
    val NameModel get() = nameModel
    val ChWhatsapp get() = chWhatsapp
    val ChTelegram get() = chTelegram
    val ChPhone get() = chPhone
    val UrlModel get() = urlModel
    val PriceModeling get() = priceModeling
    val PricePrinting get() = pricePrinting
    val Avans get() = avans
    val StatusModeling get() = statusModeling
    val StatusPrinting get() = statusPrinting
    val Payment get() = payment

*/


}

/* пример экземпляра класса
  val zap = ZapisInDB( "numberTelephone","nameMan", "nameModel",  true, false
                ,  true, "urlModel",  "priceModeling", "pricePrinting"
                , "avans", true, false, false)

 */