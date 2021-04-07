package ru.kotlin.lapki
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_testresult.*
import ru.kotlin.lapki.api.CombinationRepository
import ru.kotlin.lapki.api.PetAccountRepository
import java.time.LocalDateTime
import kotlin.math.abs


class TestResultActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testresult)
        val petID = intent.getIntExtra("id_pet", 0)
        val userID = intent.getIntExtra("id_user", 0)
        var par = arrayOf(0, 0)
        var dres = arrayOf(0, 0)
        var fam = arrayOf(0, 0)
        var around = arrayOf(0, 0)
        var ch = arrayOf(0, 0)
        var tru = arrayOf(0, 0)


        Thread{
            try {
                val userTestResponse = CombinationRepository.getUserAnswer(userID.toString())
                if ( userTestResponse.isError) throw IllegalAccessError() else {
                    println(1)
                    val petResponse = PetAccountRepository.get(petID.toString())
                    var sex: Int
                    var size = 0.0
                    if (petResponse.isError) throw IllegalAccessError() else {
                        println(2)
                        sex = if (petResponse.pet.first().sex=="Ж") 0 else 1
                        size = petResponse.pet.first().size
                    }
                    val petTestResponse = CombinationRepository.getPetAnswer(petID.toString())
                    if ( petTestResponse.isError) throw IllegalAccessError() else {
                        println(3)
                        (0..userTestResponse.answer.size-1).forEach { i ->
                            if((userTestResponse.answer[i].id_answer == 76) or (userTestResponse.answer[i].id_answer == 79)or (userTestResponse.answer[i].id_answer == 81) or (userTestResponse.answer[i].id_answer == 82) or (userTestResponse.answer[i].id_answer == 85)or (userTestResponse.answer[i].id_answer == 86)or (userTestResponse.answer[i].id_answer == 89)or (userTestResponse.answer[i].id_answer == 91)or (userTestResponse.answer[i].id_answer == 93) )
                            {
                               tru[0]+= 1
                                tru[1]+= 1
                            } else if((userTestResponse.answer[i].id_answer == 77) or (userTestResponse.answer[i].id_answer == 78)or (userTestResponse.answer[i].id_answer == 80) or (userTestResponse.answer[i].id_answer == 83) or (userTestResponse.answer[i].id_answer == 84)or (userTestResponse.answer[i].id_answer == 87)or (userTestResponse.answer[i].id_answer == 88)or (userTestResponse.answer[i].id_answer == 90)or (userTestResponse.answer[i].id_answer == 92) )
                            {
                                tru[1]+= 1
                            }
                            if ((userTestResponse.answer[i].id_answer == 63) or (userTestResponse.answer[i].id_answer == 64)or (userTestResponse.answer[i].id_answer == 65))
                            {
                                var valsex = if (userTestResponse.answer[i].id_answer == 65) 1 else {
                                    if ((userTestResponse.answer[i].id_answer == 63) and (sex ==1) ) 1 else
                                    {if ((userTestResponse.answer[i].id_answer == 64) and (sex ==0))1 else -1}
                                }
                                par[0]+= valsex
                                par[1]+= 1
                            }
                            if ((userTestResponse.answer[i].id_answer == 6) or (userTestResponse.answer[i].id_answer == 7)or (userTestResponse.answer[i].id_answer == 8)or (userTestResponse.answer[i].id_answer == 9)or (userTestResponse.answer[i].id_answer == 10))
                            {
                                var valsize =  if ((userTestResponse.answer[i].id_answer == 6) and (size >= 100.0)) 1 else {
                                    if ((userTestResponse.answer[i].id_answer == 7) and (size < 100.0)and (size >= 60.0) ) 1 else
                                    {if ((userTestResponse.answer[i].id_answer == 8) and (size < 60.0)and (size >= 25.0))1 else
                                    {if ((userTestResponse.answer[i].id_answer == 9) and (size < 25.0)and (size >= 10.0))1 else
                                    {if ((userTestResponse.answer[i].id_answer == 10) and (size < 10.0))1 else -1}}}
                                }
                                par[0]+= valsize
                                par[1]+= 1
                            }

                            (0..petTestResponse.answer.size-1).forEach { j ->
                                val combResponse = CombinationRepository.getValueComb(userTestResponse.answer[i].id_answer.toString(), petTestResponse.answer[j].id_answer.toString())
                                if (!combResponse.isError) {
                                    println(4)
                                    println(userTestResponse.answer[i].id_answer.toString())
                                    println(petTestResponse.answer[j].id_answer.toString())
                                    println(combResponse.value)
                                    val value = if(combResponse.value == -55) 0 else combResponse.value
                                    println(value)
                                    println(userTestResponse.answer[i].id_rubric)
                                    println(petTestResponse.answer[j].id_rubric)
                                    when (userTestResponse.answer[i].id_rubric){
                                        1 -> {par[0]+= value
                                            par[1]+= 1
                                        }
                                        2 -> {dres[0]+= value
                                            dres[1]+= 1
                                        }
                                        3 -> {fam[0]+= value
                                            fam[1]+= 1
                                        }
                                        4 -> {around[0]+=value
                                            around[1]+= 1
                                        }
                                        5 -> {ch[0]+= value
                                            ch[1]+= 1
                                        }
                                    }
                                    if(petTestResponse.answer[j].id_rubric!=userTestResponse.answer[i].id_rubric) {
                                        when (petTestResponse.answer[j].id_rubric) {
                                            1 -> {
                                                par[0] += value
                                                par[1] += 1
                                            }
                                            2 -> {
                                                dres[0] += value
                                                dres[1] += 1
                                            }
                                            3 -> {
                                                fam[0] += value
                                                fam[1] += 1
                                            }
                                            4 -> {
                                                around[0] += value
                                                around[1] += 1
                                            }
                                            5 -> {
                                                ch[0] += value
                                                ch[1] += 1
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    val answer1 = userTestResponse.answer
                    val answer2 = userTestResponse.answer
                    for (i in 0..answer1.size-1){
                        for (j in 0..answer2.size-1){
                            val combnegResponse = CombinationRepository.getValueCombNeg(answer1[i].id_answer.toString(), answer2[j].id_answer.toString())
                            if (!combnegResponse.isError) {
                                println(5)
                                when (answer1[i].id_rubric) {
                                    1 -> {
                                        par[0] += combnegResponse.value
                                        par[1] += abs(combnegResponse.value)
                                    }
                                    2 -> {
                                        dres[0] += combnegResponse.value
                                        dres[1] += abs(combnegResponse.value)
                                    }
                                    3 -> {
                                        fam[0] += combnegResponse.value
                                        fam[1] += abs(combnegResponse.value)
                                    }
                                    4 -> {
                                        around[0] += combnegResponse.value
                                        around[1] += abs(combnegResponse.value)
                                    }
                                    5 -> {
                                        ch[0] += combnegResponse.value
                                        ch[1] += abs(combnegResponse.value)
                                    }
                                    6 -> {
                                        tru[0] += combnegResponse.value
                                        tru[1] += abs(combnegResponse.value)
                                    }
                                }
                                if (answer2[j].id_rubric!=answer1[i].id_rubric) {
                                    when (answer2[j].id_rubric) {
                                        1 -> {
                                            par[0] += combnegResponse.value
                                            par[1] += abs(combnegResponse.value)
                                        }
                                        2 -> {
                                            dres[0] += combnegResponse.value
                                            dres[1] += abs(combnegResponse.value)
                                        }
                                        3 -> {
                                            fam[0] += combnegResponse.value
                                            fam[1] += abs(combnegResponse.value)
                                        }
                                        4 -> {
                                            around[0] += combnegResponse.value
                                            around[1] += abs(combnegResponse.value)
                                        }
                                        5 -> {
                                            ch[0] += combnegResponse.value
                                            ch[1] += abs(combnegResponse.value)
                                        }
                                        6 -> {
                                            tru[0] += combnegResponse.value
                                            tru[1] += abs(combnegResponse.value)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    val p = Result(par)
                    val d = Result(dres)
                    val f = Result(fam)
                    val a = Result(around)
                    val c = Result(ch)
                    val t = Result(tru)
                    val valueList = arrayListOf<Double>()
                    val entry = arrayListOf<BarEntry>()
                    valueList.add(p)
                    valueList.add(d)
                    valueList.add(f)
                    valueList.add(a)
                    valueList.add(c)
                    valueList.add(t)
                    valueList.add((p+d+f+a+c)/5)
                    for (i in valueList.indices) {
                        val barEntry = BarEntry(i.toFloat(), valueList[i].toFloat())
                        entry.add(barEntry)
                    }
                    val BarSet = BarDataSet(entry, "Data")
                    val ColorAr = arrayListOf<Int>()
                    (0 until valueList.size-1).forEach { i ->
                        when(valueList[i]){
                            in 0.toDouble()..40.toDouble() -> ColorAr.add(ContextCompat.getColor(this, R.color.orange))
                            in 40.toDouble()..80.toDouble() -> ColorAr.add(ContextCompat.getColor(this, R.color.yellowgreen))
                            else -> ColorAr.add(ContextCompat.getColor(this, R.color.green))
                        }
                    }
                    when(valueList[6]){
                            in 0.toDouble()..40.toDouble() -> ColorAr.add(ContextCompat.getColor(this, R.color.green))
                            in 40.toDouble()..80.toDouble() -> ColorAr.add(ContextCompat.getColor(this, R.color.yellowgreen))
                            else -> ColorAr.add(ContextCompat.getColor(this, R.color.orange))
                        }
                    runOnUiThread {

                        BarSet.setColors(ColorAr)
                        val xAxisLabel: ArrayList<String> = ArrayList()
                        xAxisLabel.add("Параметры")
                        xAxisLabel.add("Дрессировка")
                        xAxisLabel.add("Семья")
                        xAxisLabel.add("Окружение")
                        xAxisLabel.add("Характер")
                        xAxisLabel.add("Искренность")
                        xAxisLabel.add("Общий балл")
                        activity_testresult_barchart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
                        activity_testresult_barchart.xAxis.labelRotationAngle =10f
                        activity_testresult_barchart.xAxis.isGranularityEnabled = true
                        activity_testresult_barchart.xAxis.granularity = 1.0f
                        activity_testresult_barchart.xAxis.textColor = R.color.black
                        activity_testresult_barchart.xAxis.axisLineColor = R.color.black
                        activity_testresult_barchart.xAxis.gridColor = R.color.black
                        activity_testresult_barchart.description.isEnabled = false
                        BarSet.valueTextSize = 10F
                        val data = BarData(BarSet)
                        val legend = activity_testresult_barchart.legend
                        val entries: MutableList<LegendEntry> = ArrayList()
                        val colorList = arrayListOf(ContextCompat.getColor(this, R.color.orange),ContextCompat.getColor(this, R.color.yellowgreen),ContextCompat.getColor(this, R.color.green))
                        val titleList = arrayListOf("Плохой результат", "Средний результат", "Хороший результат")
                        for (i in 0 until 3) {
                            val entry = LegendEntry()
                            entry.formColor = colorList[i]
                            entry.label = titleList[i]
                            entries.add(entry)
                        }
                        legend.setCustom(entries)

                        activity_testresult_barchart.data= data
                    }
                    val setResultsResponse = CombinationRepository.setResult(
                        userID.toString(),
                        petID.toString(),
                        valueList[0].toString(),
                        valueList[1].toString(),
                        valueList[2].toString(),
                        valueList[3].toString(),
                        valueList[4].toString(),
                        valueList[5].toString(),
                        valueList[6].toString(),
                        LocalDateTime.now().toString()
                    )
                    if ( setResultsResponse.isError) throw IllegalAccessError() else {
                        println(setResultsResponse.result)
                    }

                }

            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> exception.message
                    }
                    println(err)
                }
            }
        }.start()








        val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(
            arrayOf<DataPoint>(
                DataPoint(0.0, -1.0),
                DataPoint(1.0, 5.0),
                DataPoint(2.0, 3.0),
                DataPoint(3.0, 2.0),
                DataPoint(4.0, 6.0)
            )
        )
        series.backgroundColor = resources.getColor(android.R.color.black)
        graph.setBackgroundColor(resources.getColor(android.R.color.background_light))
        graph.addSeries(series);
        val series2: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(
            arrayOf<DataPoint>(
                DataPoint(0.0, 0.0)
            )
        )
        series2.setDataPointsRadius(20f);
        series2.setDrawDataPoints(false);
        graph.addSeries(series2);

            val xvalue = ArrayList<String>()
            xvalue.add("11.00 AM")
            xvalue.add("12.00 AM")
            xvalue.add("13.00 AM")
            xvalue.add("14.00 AM")
            xvalue.add("14.00 AM")

            val entries = ArrayList<Entry>()

            entries.add(Entry(1f, 10f))
            entries.add(Entry(2f, 2f))
            entries.add(Entry(3f, 7f))
            entries.add(Entry(4f, 20f))
            entries.add(Entry(5f, 16f))
            val linedataset = LineDataSet(entries, "First")
            linedataset.color = Color.GREEN
            val data = LineData(linedataset)
            chart.data = data
            chart.setBackgroundColor(Color.WHITE)
            chart.animateXY(3000,3000)
        chart.axisLeft.mAxisMaximum = 25f


    }
    fun Result (arr: Array<Int>):Double{
        val a = arr[0].toDouble()
        val b = arr[1].toDouble()
        var r = 0.00
        r = a/b
        println("fff ${arr[0]} ${arr[1]} ${r}")
        if (arr[0].toDouble()==0.0) return 50.0 else if (arr[0].toDouble()<0.0) return (0.5*(1+arr[0].toDouble()/arr[1].toDouble()))*100 else return (0.5+0.5*arr[0].toDouble()/arr[1].toDouble())*100
    }

}
