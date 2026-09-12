package com.product.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.product.data.SessionManager
import com.product.util.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class UsageWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val sessionManager: SessionManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // Increment usage by 15 minutes
        sessionManager.incrementUsage(15)
        
        // Get current total usage
        val totalMinutes = sessionManager.usageMinutesFlow.first()
        
        // Only show notification for 30, 45, 60... minutes as requested
        if (totalMinutes >= 15) {
            NotificationHelper.showUsageNotification(applicationContext, totalMinutes)
        }
        
        return Result.success()
    }
}
