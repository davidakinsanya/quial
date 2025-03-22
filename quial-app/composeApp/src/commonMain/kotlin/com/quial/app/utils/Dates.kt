package com.quial.app.utils

import androidx.compose.runtime.mutableStateOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getCurrentDate(): String {
    val now: Instant = Clock.System.now()
    return now.toLocalDateTime(TimeZone.currentSystemDefault()).toString()
}

fun sameDateCheck(timestamp: String): Boolean {
    val stampDate = timestamp.split("T")[0].split("-")
    val currentDate = getCurrentDate().split("T")[0].split("-")
    return currentDate.toString() == stampDate.toString()
}

fun streakCheck(timestamp: String): Boolean {
    val stampDate = timestamp.split("T")[0].split("-").mapNotNull { it.toIntOrNull() }
    val currentDate = getCurrentDate().split("T")[0].split("-").mapNotNull { it.toIntOrNull() }

    return stampDate[0] == currentDate[0] &&
            stampDate[1] == currentDate[1] &&
            (currentDate[2] - stampDate[2] == 1)
}

fun getGreeting(): String {
    val greeting = mutableStateOf("Good ")
    val currentHour = getCurrentDate().split("T")[1].split(":")[0].toInt()

    when (currentHour) {
        in 0..11 -> greeting.value += "Morning"
        in 12 .. 18 -> greeting.value += "Afternoon"
        in 19 .. 23 -> greeting.value += "Evening"
    }

    return greeting.value
}

