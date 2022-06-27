package hu.bme.aut.android.liftlog.activities


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.liftlog.databinding.ActivityCalculatorBinding


class CalculatorActivity: AppCompatActivity() {
    private lateinit var binding : ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener{

            val weight = binding.etCalcWeight.text.toString().toDoubleOrNull() ?: 0.1
            val reps = binding.etCalcReps.text.toString().toDoubleOrNull()?: 0.1

            val res = (weight*(reps/30+1)).toInt()

           binding.calcResult.text = "1RM:  $res"


        }

    }


}

