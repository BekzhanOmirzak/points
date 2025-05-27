package com.task.points.presentation.points

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.*
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.task.points.R
import com.task.points.domain.model.Point
import com.task.points.presentation.App
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class PointsActivity : MvpAppCompatActivity(), PointsView {

    @Inject
    @InjectPresenter
    lateinit var daggerPresenter: PointsPresenter

    @ProvidePresenter
    fun providePresenter(): PointsPresenter = daggerPresenter

    private lateinit var countEdt: EditText
    private lateinit var btnCount: Button
    private lateinit var recView: RecyclerView
    private lateinit var pointsAdapter: PointsAdapter
    private lateinit var lineChart: LineChart
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        listeners()
    }

    private fun listeners() {
        btnCount.setOnClickListener {
            val number = countEdt.text.toString().trim().toIntOrNull() ?: 0
            daggerPresenter.fetchPoints(number)
        }

        btnSave.setOnClickListener {
            if (::lineChart.isInitialized) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        101
                    )
                } else {
                    daggerPresenter.saveChartImage(lineChart)
                }
            }
        }
    }


    private fun initViews() {
        countEdt = findViewById(R.id.edtCount)
        btnCount = findViewById(R.id.btnCount)
        recView = findViewById(R.id.pointsRecView)
        lineChart = findViewById(R.id.lineChart)
        pointsAdapter = PointsAdapter()
        recView.apply {
            layoutManager =
                LinearLayoutManager(this@PointsActivity, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = pointsAdapter
        }
        btnSave = findViewById(R.id.btnSave)
    }

    private fun setUpLineChart(points: List<Entry>) {
        val dataSet = LineDataSet(points, "Points")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK
        dataSet.circleRadius = 4f
        dataSet.lineWidth = 2f
        lineChart.data = LineData(dataSet)
        lineChart.description.isEnabled = false
        lineChart.setDrawGridBackground(false)
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.axisMinimum = -100f
        xAxis.axisMaximum = 100f

        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.axisMinimum = -100f
        yAxisLeft.axisMaximum = 100f

        lineChart.axisRight.isEnabled = false

        lineChart.setPinchZoom(true)
        lineChart.isDoubleTapToZoomEnabled = true
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.isScaleXEnabled = true
        lineChart.isScaleYEnabled = true
        lineChart.isDragXEnabled = true
        lineChart.isDragYEnabled = true

        lineChart.invalidate()
    }

    override fun showLoading(show: Boolean) {
        // TODO: need to impl loading indicator
        println("Loading is happening")
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showFetchedPoints(points: List<Point>) {
        pointsAdapter.submitList(points)
        setUpLineChart(points.map {
            Entry(it.x.toFloat(), it.y.toFloat())
        })
    }

}