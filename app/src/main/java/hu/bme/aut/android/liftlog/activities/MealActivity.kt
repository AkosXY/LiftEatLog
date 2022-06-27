package hu.bme.aut.android.liftlog.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import hu.bme.aut.android.liftlog.data.MealList
import hu.bme.aut.android.liftlog.databinding.ActivityMealBinding
import hu.bme.aut.android.liftlog.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealActivity : AppCompatActivity(){
    private lateinit var binding : ActivityMealBinding
    private var link: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMeal()

        binding.btnRefresh.setOnClickListener(){
            getMeal()
        }

        binding.ibMeal.setOnClickListener(){
            if(!link.isNullOrBlank()){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }
        }

    }


    private fun getMeal(){
        NetworkManager.mealApi.getRandomMeal().enqueue(object:Callback<MealList>{

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val meal = response.body()!!.meals[0]
                    Log.d("test", "meal_name: ${meal.strMeal}")

                    Glide.with(this@MealActivity).load(meal.strMealThumb).into(binding.ibMeal)

                    binding.tvMeal.text = meal.strMeal

                    link = meal.strYoutube
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("test", t.message.toString())

                link = ""
                Toast.makeText(this@MealActivity, "Check internet connection", Toast.LENGTH_LONG).show()


            }

        })
    }





}


