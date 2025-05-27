package com.task.points.data.repository

import com.task.points.data.api.PointsApi
import com.task.points.data.dto.toPointModel
import com.task.points.domain.model.Point
import com.task.points.domain.repository.PointsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PointsRepositoryImpl @Inject constructor(
    private val pointsApi: PointsApi
) : PointsRepository {

    override fun fetchPoints(count: Int): Single<List<Point>> {
        return pointsApi.fetchPoints(count).map { response ->
            response.points.map { it.toPointModel() }
        }
    }

}