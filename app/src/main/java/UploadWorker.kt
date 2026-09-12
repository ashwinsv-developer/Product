//package com.example.mywork.sample
//
//import android.content.Context
//import android.util.Log
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import com.example.mywork.model.WorkData
//import com.example.mywork.model.WorkResult
//import com.example.mywork.model.Worker
//import com.example.mywork.model.WorkerParameters
//import kotlinx.coroutines.delay
//
///**
// * Example Worker. Must have a public (Context, WorkerParameters) constructor —
// * that's the contract WorkerFactory relies on via reflection.
// */
//class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
//
//    override suspend fun doWork(): WorkResult {
//        val fileName = inputData.getString("file_name") ?: return WorkResult.Failure
//
//        return try {
//            Log.d("UploadWorker", "Uploading $fileName (attempt #$runAttemptCount)")
//            delay(2000) // simulate network call
//            // ... real upload logic here ...
//            WorkResult.Success
//        } catch (e: Exception) {
//            if (runAttemptCount < 3) WorkResult.Retry else WorkResult.Failure
//        }
//    }
//
//    override fun outputData(): WorkData =
//        WorkData.Builder().putString("uploaded_url", "https://example.com/file").build()
//}
