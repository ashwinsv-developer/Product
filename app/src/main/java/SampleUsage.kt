//package com.example.mywork.sample
//
//import android.content.Context
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.lifecycleScope
//import androidx.work.BackoffPolicy
//import androidx.work.Constraints
//import androidx.work.NetworkType
//import androidx.work.WorkRequest
//import androidx.work.WorkRequest.Builder
//
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.launch
//
//class SampleUsage(private val context: Context, private val owner: LifecycleOwner) {
//
//    fun enqueueSimpleUpload() {
//        val request = Builder(UploadWorker::class.java.name)
//            .setInputData(WorkData.Builder().putString("file_name", "report.pdf").build())
//            .setConstraints(Constraints(networkType = NetworkType.CONNECTED))
//            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10_000L)
//            .addTag("uploads")
//            .build()
//
//        val workManager = MyWorkManager.getInstance(context)
//        workManager.enqueue(request)
//
//        // Observe its state (e.g. update UI when it finishes)
//        workManager.observeWorkState(request.id)
//            .onEach { state -> /* update UI, e.g. show spinner until SUCCEEDED */ }
//            .launchIn(owner.lifecycleScope)
//    }
//
//    fun enqueueUniqueSync() {
//        val request = Builder(UploadWorker::class.java.name)
//            .setConstraints(Constraints(networkType = NetworkType.UNMETERED))
//            .build()
//
//        // REPLACE means: if a "daily-sync" job already exists, cancel it and start fresh.
//        MyWorkManager.getInstance(context)
//            .enqueueUnique("daily-sync", ExistingWorkPolicy.REPLACE, request)
//    }
//
//    fun enqueueChainedWork() {
//        val step1 = Builder(UploadWorker::class.java.name)
//            .setInputData(WorkData.Builder().putString("file_name", "part1.pdf").build())
//            .build()
//        val step2 = Builder(UploadWorker::class.java.name)
//            .setInputData(WorkData.Builder().putString("file_name", "part2.pdf").build())
//            .build()
//
//        // step2 will only run after step1 reaches SUCCEEDED.
//        MyWorkManager.getInstance(context).enqueueChain(listOf(step1, step2))
//    }
//}
