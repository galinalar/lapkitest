package ru.kotlin.lapki
import android.content.Context
import android.graphics.Color.red
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_testresult.*
import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.highlight.Highlight


class TestResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testresult)
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

}
