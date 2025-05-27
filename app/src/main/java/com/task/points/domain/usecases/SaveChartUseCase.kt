package com.task.points.domain.usecases

import com.github.mikephil.charting.charts.LineChart
import com.task.points.domain.repository.SaveChartRepository
import javax.inject.Inject

class SaveChartUseCase @Inject constructor(
    private val saveChartRepository: SaveChartRepository
) {

    operator fun invoke(lineChart: LineChart, fileName: String) =
        saveChartRepository.saveChart(lineChart, fileName)

}