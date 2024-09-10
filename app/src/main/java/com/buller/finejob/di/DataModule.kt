package com.buller.finejob.di

import com.buller.data.api.ApiConstants
import com.buller.data.api.VacanciesApiService
import com.buller.data.repository.VacancyRepositoryImpl
import com.buller.domain.interfaces.VacanciesSource
import com.buller.data.source.VacanciesSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {
    single {
        VacancyRepositoryImpl(vacanciesSource = get())
    }

    single {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single<VacanciesSource> {
        VacanciesSourceImpl(apiService = get())
    }

    single<VacanciesApiService> { get<Retrofit>().create(VacanciesApiService::class.java) }

    //TODO как происходит логированние
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


}