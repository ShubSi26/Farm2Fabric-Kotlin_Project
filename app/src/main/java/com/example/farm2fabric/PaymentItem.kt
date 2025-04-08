package com.example.farm2fabric

import java.util.Date

data class PaymentItem (
    val id : String,
    val amount : Number,
    val status : String,
    val customerid : String,
    val quantity : Number,
    val order_id : String,
    val paymentid : String,
    val date : Date,
    val consignment_id : String,
    )