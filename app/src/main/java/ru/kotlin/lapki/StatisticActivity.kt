package ru.kotlin.lapki

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.activity_statistic.*


class StatisticActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        val textu = "Сколько времени в неделю вы готовы уделять собаке?"
        val question:Array<String> = arrayOf(textu)
        activity_statistic_spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, question)
        showPieChart()
        showBar()
        showLine()
    }
    private fun showPieChart() {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["более 20 часов"] = 10
        typeAmountMap["20 часов"] = 7
        typeAmountMap["15 часов"] = 3
        typeAmountMap["7 часов"] = 6
        typeAmountMap["меньше 7 часов"] = 2

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(this,R.color.lightb))
        colors.add(ContextCompat.getColor(this,R.color.lightgreen))
        colors.add(ContextCompat.getColor(this,R.color.green))
        colors.add(ContextCompat.getColor(this,R.color.yellowgreen))
        colors.add(ContextCompat.getColor(this,R.color.orange))
        colors.add(ContextCompat.getColor(this,R.color.white))

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        //setting text size of the value
        pieDataSet.valueTextSize = 12f
        //providing color list for coloring different entries
        pieDataSet.colors = colors
        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true)
        pieData.setValueFormatter(PercentFormatter())
        activity_statistic_piechart.data = pieData
        activity_statistic_piechart.description.isEnabled = false
        activity_statistic_piechart.isRotationEnabled = true
        activity_statistic_piechart.dragDecelerationFrictionCoef = 0.9f
        activity_statistic_piechart.setHoleColor(
            ContextCompat.getColor(this,R.color.background))
        activity_statistic_piechart.invalidate()
    }
    private fun showBar(){
        val valueList = arrayListOf<Double>()
        val entry = arrayListOf<BarEntry>()
        valueList.add(100.00)
        valueList.add(50.00)
        valueList.add(66.00)
        valueList.add(55.00)
        valueList.add(90.00)
        valueList.add(80.00)
        for (i in valueList.indices) {
            val barEntry = BarEntry(i.toFloat(), valueList[i].toFloat())
            entry.add(barEntry)
        }
        val BarSet = BarDataSet(entry, "Data")
        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(this,R.color.lightb))
        colors.add(ContextCompat.getColor(this,R.color.lightgreen))
        colors.add(ContextCompat.getColor(this,R.color.green))
        colors.add(ContextCompat.getColor(this,R.color.yellowgreen))
        colors.add(ContextCompat.getColor(this,R.color.orange))
        colors.add(ContextCompat.getColor(this,R.color.white))
            BarSet.setColors(colors)
            val xAxisLabel: ArrayList<String> = ArrayList()
            xAxisLabel.add("Друг")
            xAxisLabel.add("Верность")
            xAxisLabel.add("HopeWorld")
            xAxisLabel.add("Окружение")
            xAxisLabel.add("Love")
            xAxisLabel.add("Дом")
            activity_statistic_barchart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
            activity_statistic_barchart.xAxis.labelRotationAngle =10f
        activity_statistic_barchart.xAxis.isGranularityEnabled = true
        activity_statistic_barchart.xAxis.granularity = 1.0f
        activity_statistic_barchart.xAxis.textColor = R.color.black
        activity_statistic_barchart.xAxis.axisLineColor = R.color.black
        activity_statistic_barchart.xAxis.gridColor = R.color.black

        activity_statistic_barchart.description.isEnabled = false
            BarSet.valueTextSize = 10F
            val data = BarData(BarSet)
            val legend = activity_statistic_barchart.legend
            val entries: MutableList<LegendEntry> = ArrayList()
            for (i in 0 until xAxisLabel.size) {
                val entry = LegendEntry()
                entry.formColor = colors[i]
                entry.label = xAxisLabel[i]
                entries.add(entry)
            }
            legend.setCustom(entries)

        activity_statistic_barchart.data= data
        }
    private fun showLine(){
        val xvalue = ArrayList<String>()
        xvalue.add("06.00-10.00")
        xvalue.add("10.00-13.00")
        xvalue.add("13.00-15.00")
        xvalue.add("15.00-18.00")
        xvalue.add("18.00-21.00")
        xvalue.add("21.00-00.00")
        xvalue.add("00.00-06.00")

        val entries = ArrayList<Entry>()

        entries.add(Entry(0f, 10f))
        entries.add(Entry(1f, 5f))
        entries.add(Entry(2f, 13f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 10f))
        entries.add(Entry(5f, 8f))
        entries.add(Entry(6f, 2f))
        val linedataset = LineDataSet(entries, "Динамика подачи заявок")
        linedataset.color = ContextCompat.getColor(this,R.color.black)
        val data = LineData(linedataset)
        activity_statistic_chart.data = data
        activity_statistic_chart.setBackgroundColor(ContextCompat.getColor(this,R.color.background))
        activity_statistic_chart.animateXY(3000,3000)
        activity_statistic_chart.xAxis.valueFormatter= IndexAxisValueFormatter(xvalue)
    }
}