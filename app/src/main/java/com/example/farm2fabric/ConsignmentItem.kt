package com.example.farm2fabric

import java.util.Date

data class ConsignmentItem(
    val id:String,
    val consignmentId: String,
    val status: String,
    val paymentid: String,
    val paymentstatus: String,
    val customerid: String,
    val quantity: Number,
    val price: Number,
    val trackingid: String,
    val date: Date
)
