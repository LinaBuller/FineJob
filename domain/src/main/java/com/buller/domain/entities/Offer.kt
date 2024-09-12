package com.buller.domain.entities

data class Offer(
    val button: Button,
    val id: String,
    val link: String,
    val title: String
):DisplayableItem