package com.task.points.presentation.points

import com.github.mikephil.charting.charts.LineChart
import com.task.points.domain.model.Point
import com.task.points.domain.usecases.GetPointsUseCases
import com.task.points.domain.usecases.SaveChartUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class PointsPresenter @Inject constructor(
    private val getPointsUseCase: GetPointsUseCases,
    private val saveChartUseCase: SaveChartUseCase
) : MvpPresenter<PointsView>() {

    private val disposables = CompositeDisposable()

    fun fetchPoints(count: Int) {
        disposables.add(getPointsUseCase(count)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showLoading()
            }
            .doFinally {
                viewState.showLoading(false)
            }
            .subscribe(
                { points: List<Point> ->
                    viewState.showFetchedPoints(points.sortedBy { it.x })
                }, { error ->
                    viewState.showMessage(error.message ?: "Unknown error")
                }
            )
        )
    }

    fun saveChartImage(lineChart: LineChart) {
        disposables.add(saveChartUseCase(lineChart, "line_chart_${System.currentTimeMillis()}.png")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.showLoading()
            }
            .doFinally {
                viewState.showLoading(false)
            }
            .subscribe(
                { path ->
                    viewState.showMessage("Chart saved to: $path")
                },
                { error ->
                    viewState.showMessage("Failed to save: ${error.message}")
                }
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}