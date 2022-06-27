package hu.bme.aut.android.liftlog.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.liftlog.databinding.ActivityMenuBinding

class MenuActivity: AppCompatActivity() {

    private lateinit var binding : ActivityMenuBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnMeal.setOnClickListener {
            val mealIntent = Intent(this, MealActivity::class.java)
            startActivity(mealIntent)
        }

        binding.btnNewLift.setOnClickListener {
            val profileIntent = Intent(this, MainActivity::class.java)
            startActivity(profileIntent)
        }


        binding.btnCalculator.setOnClickListener {
            val calculatorIntent = Intent(this, CalculatorActivity::class.java)
            startActivity(calculatorIntent)
        }

        binding.btnChart.setOnClickListener {
            val chartIntent = Intent(this, ChartActivity::class.java)
            startActivity(chartIntent)
        }





    }



}