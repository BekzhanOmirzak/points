package com.task.points.data.api

import com.task.points.data.dto.PointsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApi {

    @GET("api/test/points")
    fun fetchPoints(@Query("count") count: Int): Single<PointsResponse>

}