package com.example.hhru


import retrofit2.Call
import retrofit2.http.GET

interface OfferVacancyApi {
    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    fun getOfferVacancy(): Call<OfferVacancyResponse>
}