package com.example.music

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MusicPlayerContract.View {
    private lateinit var presenter: MusicPlayerPresenter
    private lateinit var adapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MusicPlayerPresenter(this, MusicList(this))

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSongs)
        adapter = SongAdapter { presenter.onSongSelected(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        bindEvents(presenter)
        presenter.onViewReady()
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
        val intent = Intent(this, MusicService::class.java)
        intent.action = if (isPlaying) MusicService.ACTION_PLAY else MusicService.ACTION_PAUSE
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