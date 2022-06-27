package hu.bme.aut.android.liftlog.activities

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import hu.bme.aut.android.liftlog.R
import hu.bme.aut.android.liftlog.data.LiftingItem
import hu.bme.aut.android.liftlog.data.LiftingListDatabase
import hu.bme.aut.android.liftlog.databinding.ActivityChartBinding
import kotlin.concurrent.thread


class ChartActivity: AppCompatActivity() {
    private lateinit var binding :ActivityChartBinding
    private lateinit var database: LiftingListDatabase
    
    private var benchCount = 0
    private var squatCount = 0
    private var deadliftCount = 0
    
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = LiftingListDatabase.getDatabase(applicationContext)
        fillChart()

    }


    private fun fillChart(){
        benchCount = 0
        squatCount = 0
        deadliftCount = 0

        thread {
            val tmp = database.liftingItemDao().getAll()
            for(item in tmp){
                when (item.exercise) {
                    LiftingItem.Category.SQUAT -> {
                        squatCount += 1
                    }
                    LiftingItem.Category.DEADLIFT -> {
                        deadliftCount += 1
                    }
                    LiftingItem.Category.BENCH -> {
                        benchCount += 1
                    }
                }
            }

            loadChartData()
        }

    }

    private fun loadChartData(){

        val entries = listOf(
            PieEntry(benchCount.toFloat(), getString(R.string.benchPie)),
            PieEntry(deadliftCount.toFloat(), getString(R.string.deadliftPie)),
            PieEntry(squatCount.toFloat(), getString(R.string.squatPie))
        )
        val dataSet = PieDataSet(entries, " ")

        val data = PieData(dataSet)

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#ff8300"))
        colors.add(Color.parseColor("#e84f31"))
        colors.add(Color.parseColor("#edd142"))

        dataSet.colors = colors

        dataSet.valueTextSize = 25f
        dataSet.valueTextColor = Color.WHITE
        dataSet.formSize = 25f
        dataSet.valueFormatter = DefaultValueFormatter(0)


        binding.chartLift.data = data

        setChart()
        setLegends()

        binding.chartLift.invalidate()

    }

    private fun setChart(){
        binding.chartLift.description.text = " "
        binding.chartLift.holeRadius = 60f
        binding.chartLift.transparentCircleRadius = 63f
        binding.chartLift.isDrawHoleEnabled = true
        binding.chartLift.setHoleColor(Color.WHITE)
        binding.chartLift.setDrawCenterText(true)
        binding.chartLift.centerText = getString(R.string.chartLiftsPerformed)
        binding.chartLift.setCenterTextSize(15f)

    }


    private fun setLegends() {
        val l: Legend = binding.chartLift.legend
        l.textSize = 15f
        l.isWordWrapEnabled = true
        l.textColor = Color.BLACK
        l.form = Legend.LegendForm.CIRCLE
        l.xEntrySpace = 25f
        l.orientation = Legend.LegendOrientation.VERTICAL

        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP


        binding.chartLift.legend.orientation = Legend.LegendOrientation.HORIZONTAL

    }


}