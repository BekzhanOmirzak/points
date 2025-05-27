package com.task.points.presentation.points

import com.task.points.domain.model.Point
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface PointsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(show: Boolean = true)

    @StateStrategyType(SkipStrategy::class)
    fun showMessage(msg: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showFetchedPoints(points: List<Point>)


}