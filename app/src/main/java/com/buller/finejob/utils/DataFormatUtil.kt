package com.buller.finejob.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DataFormatUtil {

    fun formatPublishedDate(dateString: String): String {
        val date = LocalDate.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
        return date.format(formatter)
    }
}