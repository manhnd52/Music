package com.example.music

import android.content.Context
import android.media.MediaPlayer

class MusicList() : MusicPlayerModel {
    private val songList = listOf(
        Song("Chú voi con ở bản Đôn", "ABC", R.raw.chu_voi_con),
        Song("Bắc Kim Thang", "Bé Bào Ngư", R.raw.bac_kim_thang),
        Song("Cả Nhà Thương Nhau", "Bé Bào Ngư", R.raw.ca_nha_thuong_nhau)
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