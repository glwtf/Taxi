package com.example.taxi.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import java.io.File
import java.util.concurrent.TimeUnit

class DeleteWorker(
    context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

    override suspend  fun doWork(): Result {
        val fullImagePath = workerParameters.inputData.getString(IMAGE_FULL_PATH)
        if (fullImagePath != null) {
            val progress = "Starting Download"
            deletePhoto(fullImagePath)
            setForeground(createForegroundInfo(progress))
        }
        return Result.success()
    }

    private fun deletePhoto(fullImagePath : String) = File(fullImagePath).delete()

    private fun createForegroundInfo(progress: String): ForegroundInfo {
        return ForegroundInfo(NOTIFICATION_ID, notification(progress))
    }

    private fun notification(progress: String) : Notification {
        val id = CHANEL_ID
        val title = NOTIFICATION_TITLE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        return NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(android.R.drawable.stat_sys_warning)
            .setOngoing(true)
            .build()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val chan = NotificationChannel(CHANEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(chan)
    }

    companion object {

        private const val IMAGE_FULL_PATH = "imageFullPath"
        private const val CHANEL_ID = "channelId"
        private const val CHANEL_NAME = "channelName"
        private const val NOTIFICATION_TITLE = "Taxi"
        private const val NOTIFICATION_ID = 2

        fun makeRequest(fullImagePath: String): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<DeleteWorker>()
                .setInputData(workDataOf(IMAGE_FULL_PATH to fullImagePath))
                .setInitialDelay(10, TimeUnit.MINUTES)
                .build()
        }
    }

}