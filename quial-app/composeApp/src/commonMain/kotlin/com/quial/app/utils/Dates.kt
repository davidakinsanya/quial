package com.quial.app.utils

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