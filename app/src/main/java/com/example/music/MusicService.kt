package com.example.music

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    private lateinit var model: MusicPlayerModel
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        model = MusicList(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> model.getCurrentSong()?.let { model.play(it); notify(it, true) }
            ACTION_PAUSE -> model.getCurrentSong()?.let { model.pause(); notify(it, false) }
            ACTION_NEXT -> { model.next(); model.getCurrentSong()?.let { notify(it, true) } }
            ACTION_PREV -> { model.previous(); model.getCurrentSong()?.let { notify(it, true) } }
        }
        return START_STICKY
    }

    private fun notify(song: Song, isPlaying: Boolean) {
        val notification = NotificationHelper.createNotification(this, song, isPlaying)
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREV = "ACTION_PREV"
    }
}