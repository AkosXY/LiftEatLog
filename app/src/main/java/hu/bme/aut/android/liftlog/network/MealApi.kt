package hu.bme.aut.android.liftlog.network

import hu.bme.aut.android.liftlog.data.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>


}