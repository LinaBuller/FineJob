package com.buller.domain.entities

data class ResponseInfo(
    val vacancyList: List<Vacancy>,
    val offerList: List<Offer>
)