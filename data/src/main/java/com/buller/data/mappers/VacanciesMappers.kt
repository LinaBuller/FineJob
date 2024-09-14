package com.buller.data.mappers

import com.buller.data.dto.ResponseDto
import com.buller.domain.entities.Address
import com.buller.domain.entities.Button
import com.buller.domain.entities.Experience
import com.buller.domain.entities.Offer
import com.buller.domain.entities.ResponseInfo
import com.buller.domain.entities.Salary
import com.buller.domain.entities.Vacancy

fun ResponseDto.toVacanciesList(): List<Vacancy> {
    val vacanciesList = mutableListOf<Vacancy>()
    vacancies.map { vacancyDto ->

        val address = Address(
            house = vacancyDto.address.house,
            street = vacancyDto.address.street,
            town = vacancyDto.address.town
        )

        val experience = Experience(
            text = vacancyDto.experience.text,
            previewText = vacancyDto.experience.previewText
        )

        val salary = Salary(full = vacancyDto.salary.full, short = vacancyDto.salary.short)

        val vacancy = Vacancy(
            id = vacancyDto.id,
            title = vacancyDto.title,
            description = vacancyDto.description,
            responsibilities = vacancyDto.responsibilities,
            isFavorite = vacancyDto.isFavorite,
            address = address,
            appliedNumber = vacancyDto.appliedNumber,
            company = vacancyDto.company,
            experience = experience,
            lookingNumber = vacancyDto.lookingNumber,
            publishedDate = vacancyDto.publishedDate,
            questions = vacancyDto.questions,
            salary = salary,
            schedules = vacancyDto.schedules
        )
        vacanciesList.add(vacancy)
    }
    return vacanciesList
}

fun ResponseDto.toOffersList(): List<Offer> {
    val offerList = mutableListOf<Offer>()
    offers.map { offerDto ->

        val button = Button(text = offerDto.button?.text)

        val offer = Offer(
            id = offerDto.id,
            link = offerDto.link,
            title = offerDto.title,
            button = button
        )
        offerList.add(offer)
    }
    return offerList
}

fun ResponseDto.toResponseInfo(): ResponseInfo {
    val vacancyList = toVacanciesList()
    val offerList = toOffersList()
    return ResponseInfo(vacancyList = vacancyList, offerList = offerList)
}