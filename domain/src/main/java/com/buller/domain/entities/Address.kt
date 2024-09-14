package com.buller.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val house: String,
    val street: String,
    val town: String
):Parcelable