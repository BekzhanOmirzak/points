package com.task.points.domain.repository

import com.github.mikephil.charting.charts.LineChart
import io.reactivex.rxjava3.core.Single

interface SaveChartRepository {

    fun saveChart(chart: LineChart, fileName: String): Single<String>

}