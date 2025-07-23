package com.example.music

interface MusicPlayerModel {
    fun getSongs(): List<Song>
    fun play(song: Song)
    fun pause()
    fun next()
    fun previous()
    fun getCurrentSong(): Song?
    fun isPlaying(): Boolean
}