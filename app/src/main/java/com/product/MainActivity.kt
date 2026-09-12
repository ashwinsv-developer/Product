package com.product

import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.product.data.SessionManager
import com.product.navigation.AppNavGraph
import com.product.navigation.Home
import com.product.navigation.Login
import com.product.ui.theme.ProductTheme
import com.product.worker.UsageWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.os.Bundle

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager
    private val mainViewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            scheduleUsageTracking()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNotificationPermission()

        enableEdgeToEdge()
        setContent {
            ProductTheme {
                val isLoggedIn by sessionManager.isLoggedInFlow.collectAsState(initial = false)
                val navController = rememberNavController()
                val startDestination =
                    if (isLoggedIn) {
                        Home
                    } else {
                        Login
                    }
                AppNavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }

    private fun scheduleUsageTracking() {
        val usageWorkRequest = PeriodicWorkRequestBuilder<UsageWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "UsageTrackingWork",
            ExistingPeriodicWorkPolicy.KEEP,
            usageWorkRequest
        )
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                scheduleUsageTracking()
            }
        } else {
            scheduleUsageTracking()
        }
    }
}