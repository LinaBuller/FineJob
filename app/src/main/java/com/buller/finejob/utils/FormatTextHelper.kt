package com.buller.finejob.utils

object FormatTextHelper {

    fun makeFormattedStroke(stroke:List<String>): String {
        val strBuilder = StringBuilder()
        val strSeparator = ", "
        stroke.forEachIndexed { index, str ->
            strBuilder.append(str)
            if (index < stroke.size - 1) {
                strBuilder.append(strSeparator)
            }
            strBuilder.append(strSeparator)
        }
        return strBuilder.toString()
    }
}