package com.buller.finejob.utils

import android.content.res.Resources

object PluralsHelper {
    fun getPlurals(resources: Resources, res :Int, count: Int): String {
        return resources.getQuantityString(res, count, count)
    }
}