package com.example.firelogin2.data

import com.example.firelogin2.utils.TimeConverter
import java.time.LocalDateTime

data class MoneyData(
    val uid:String = "",
    val amount:String = "",
    val description:String = "",
    val time: String = TimeConverter(LocalDateTime.now())
)
