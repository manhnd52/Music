package com.example.music

import android.content.Context
import android.media.MediaPlayer

class MusicList(private val context: Context) : MusicPlayerModel {
    private val songList = listOf(
        Song("Escape (The Piña Colada Song)", "Rupert Holmes"),
        Song("September", "Earth, Wind & Fire"),
        Song("Again & Again", "Bazzi")
    )
    private var currentIndex = 0
    private var isPlaying = false

    override fun getSongs(): List<Song> = songList
    override fun play(song: Song) {
        currentIndex = songList.indexOf(song)
        isPlaying = true
    }

    override fun pause() {
        isPlaying = false
    }

    override fun next() {
        currentIndex = (currentIndex + 1) % songList.size
        isPlaying = true
    }

    override fun previous() {
        currentIndex = if (currentIndex - 1 < 0) songList.lastIndex else currentIndex - 1
        isPlaying = true
    }

    override fun getCurrentSong(): Song? = songList.getOrNull(currentIndex)

    override fun isPlaying() = isPlaying
}