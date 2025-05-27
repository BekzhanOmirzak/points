package com.task.points.domain.repository

import com.task.points.domain.model.Point
import io.reactivex.rxjava3.core.Single

interface PointsRepository {

    fun fetchPoints(count: Int): Single<List<Point>>

}