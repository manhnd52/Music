package com.example.music

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat

object NotificationHelper {
    fun createNotification(context: Context, song: Song, isPlaying: Boolean): Notification {
        val playIntent = Intent(context, MusicService::class.java).apply {
            action = if (isPlaying) MusicService.ACTION_PAUSE else MusicService.ACTION_PLAY
        }
        val playPendingIntent = PendingIntent.getService(context, 0, playIntent, PendingIntent.FLAG_IMMUTABLE)

        val nextIntent = Intent(context, MusicService::class.java).apply { action = MusicService.ACTION_NEXT }
        val nextPendingIntent = PendingIntent.getService(context, 1, nextIntent, PendingIntent.FLAG_IMMUTABLE)

        val prevIntent = Intent(context, MusicService::class.java).apply { action = MusicService.ACTION_PREV }
        val prevPendingIntent = PendingIntent.getService(context, 2, prevIntent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "music_channel")
            .setContentTitle(song.title)
            .setContentText(song.artist)
            .setSmallIcon(R.drawable.ic_music)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, song.imageRes))
            .addAction(R.drawable.ic_prev, "Prev", prevPendingIntent)
            .addAction(
                if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play,
                if (isPlaying) "Pause" else "Play",
                playPendingIntent
            )
            .addAction(R.drawable.ic_next, "Next", nextPendingIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)

        return builder.build()
    }
}