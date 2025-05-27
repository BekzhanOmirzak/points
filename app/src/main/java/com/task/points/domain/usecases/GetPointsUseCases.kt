package com.task.points.domain.usecases

import com.task.points.domain.repository.PointsRepository
import javax.inject.Inject

class GetPointsUseCases @Inject constructor(
    private val pointsRepository: PointsRepository
) {

    operator fun invoke(count: Int) =
        pointsRepository.fetchPoints(count)
}
