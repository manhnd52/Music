package com.example.music

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music.MusicService.Companion.ACTION_NEXT
import com.example.music.MusicService.Companion.ACTION_PREV

class MainActivity : AppCompatActivity(), MusicPlayerContract.View {
    private lateinit var presenter: MusicPlayerPresenter
    private lateinit var adapter: SongAdapter

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.v("MyTag", "onReceive Broadcast")
            if (intent?.action == "UPDATE_UI") {
                val status = intent.getStringExtra("status")
                when (status) {
                    ACTION_NEXT -> presenter.onNextClicked()
                    ACTION_PREV -> presenter.onPreviousClicked()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(receiver, IntentFilter("UPDATE_UI"), RECEIVER_EXPORTED)

        setContentView(R.layout.activity_main)

        presenter = MusicPlayerPresenter(this, MusicList())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSongs)
        adapter = SongAdapter { presenter.onSongSelected(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        bindEvents(presenter)
        presenter.onViewReady()
    }

    override fun onStop() {
        super.onStop()
        Log.v("MyTag", "mainActivity onStop")
    }
    override fun showSongs(songs: List<Song>) {
        adapter.submitList(songs)
    }

    override fun updateMiniPlayer(song: Song, isPlaying: Boolean) {
        findViewById<TextView>(R.id.textTitle).text = song.title
        findViewById<TextView>(R.id.textArtist).text = song.artist
        findViewById<ImageView>(R.id.imageSong).setImageResource(song.imageRes)

        val playPauseBtn = findViewById<ImageButton>(R.id.btnPlayPause)
        playPauseBtn.setImageResource(
            if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        )
    }

    override fun updateNotification(song: Song, isPlaying: Boolean) {
        Log.v("MyTag", "${song.title} is ${if (isPlaying) "playing" else "pausing"}!")
        val intent = Intent(this, MusicService::class.java).apply {
            action = if (isPlaying) MusicService.ACTION_PLAY else MusicService.ACTION_PAUSE
            putExtra(MusicService.SONG_DATA, song)
        }
        startService(intent)
    }

    override fun bindEvents(presenter: MusicPlayerContract.Presenter) {
        findViewById<ImageButton>(R.id.btnPlayPause).setOnClickListener {
            presenter.onPlayPauseClicked()
        }
        findViewById<ImageButton>(R.id.btnNext).setOnClickListener {
            presenter.onNextClicked()
        }
        findViewById<ImageButton>(R.id.btnPrev).setOnClickListener {
            presenter.onPreviousClicked()
        }
    }
}