package com.task.points.data.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import com.github.mikephil.charting.charts.LineChart
import com.task.points.domain.repository.SaveChartRepository
import io.reactivex.rxjava3.core.Single
import java.io.IOException
import javax.inject.Inject

class SaveChartRepositoryImpl @Inject constructor(
    private val context: Context
) : SaveChartRepository {

    override fun saveChart(chart: LineChart, fileName: String): Single<String> {
        return Single.create { emitter ->
            try {
                val bitmap = chart.chartBitmap
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.png")
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        "${Environment.DIRECTORY_PICTURES}/Charts"
                    )
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }

                val contentResolver = context.contentResolver
                val uri = contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )

                if (uri != null) {
                    contentResolver.openOutputStream(uri).use { outputStream ->
                        outputStream?.let {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        }
                    }

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    contentResolver.update(uri, contentValues, null, null)

                    emitter.onSuccess(uri.toString())
                } else {
                    emitter.onError(IOException("Failed to create image file"))
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

}