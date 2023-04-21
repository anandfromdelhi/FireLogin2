package com.example.firelogin2.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun TimeConverter(
    time: LocalDateTime
): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return time.format(formatter)
}