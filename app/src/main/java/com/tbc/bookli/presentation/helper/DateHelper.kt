package com.tbc.bookli.presentation.helper

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateHelper {
    fun getCurrentDateFormatted(): String {
        val currentMoment = Clock.System.now()
        val currentDate = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return currentDate.toString()
    }
}
