package com.example.ghtk_leak_memory

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.util.concurrent.Executors

class MyForegroundService : Service() {

    private val executor = Executors.newSingleThreadExecutor()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Tạo thông báo để đưa service vào foreground
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)

        // Bắt đầu công việc nặng trên luồng nền
        executor.execute {
            performHeavyTask()
            // Dừng service khi công việc hoàn thành
        }

        return START_STICKY
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "MY_CHANNEL_ID"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationChannelId, "My Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("My Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    private fun performHeavyTask() {
        // Ví dụ: Tác vụ nặng (giả lập với sleep)
        for (i in 1..10) {
            Thread.sleep(1000) // Mô phỏng công việc nặng bằng sleep
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Dọn dẹp tài nguyên nếu cần
        executor.shutdown()
    }

    companion object {
        const val NOTIFICATION_ID = 1
    }
}