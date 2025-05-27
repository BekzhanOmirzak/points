package com.task.points.data.dto

import com.task.points.domain.model.Point

data class PointDto(
    val x: Double,
    val y: Double
)

data class PointsResponse(
    val points: List<PointDto>
)

fun PointDto.toPointModel() = Point(x, y)