package hu.bme.aut.android.liftlog.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient


object NetworkManager {

    private val retrofit: Retrofit
    val mealApi: MealApi

    private const val baseUrl = "https://www.themealdb.com/api/json/v1/1/"


    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mealApi = retrofit.create(MealApi::class.java)

    }



}