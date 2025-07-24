package com.example.music

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var currentSong: Song
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            ACTION_PLAY -> {
                val song = intent.getParcelableExtra<Song>(SONG_DATA) ?: return START_NOT_STICKY
                if (mediaPlayer == null ) {
                    mediaPlayer = MediaPlayer.create(this, song.resourceId)
                } else if (song.title != currentSong.title) {
                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer.create(this, song.resourceId)
                }
                currentSong = song
                mediaPlayer?.start()
                notify(song, true)
            }
            ACTION_RESUME -> {
                mediaPlayer?.start()
                notify(currentSong, true)
                return START_NOT_STICKY
            }
            ACTION_PAUSE -> {
                mediaPlayer?.pause()
                notify(currentSong, false)
            }
            ACTION_PREV -> {
                val updateIntent = Intent("UPDATE_UI").apply {
                    putExtra("status", ACTION_PREV)
                }
                sendBroadcast(updateIntent)
            }
            ACTION_NEXT -> {
                val updateIntent = Intent("UPDATE_UI").apply {
                    putExtra("status", ACTION_NEXT)
                }
                sendBroadcast(updateIntent)
            }
        }
        return START_NOT_STICKY
    }

    private fun notify(song: Song, isPlaying: Boolean) {
        val notification = NotificationHelper.createNotification(this, song, isPlaying)
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    companion object {
        const val SONG_DATA = "SONG_DATA"
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_RESUME = "ACTION_RESUME"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREV = "ACTION_PREV"
    }
}