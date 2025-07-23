package com.example.music

interface MusicPlayerContract {
    interface View {
        fun showSongs(songs: List<Song>)
        fun updateMiniPlayer(song: Song, isPlaying: Boolean)
        fun updateNotification(song: Song, isPlaying: Boolean)
        fun bindEvents(presenter: Presenter)
    }

    interface Presenter {
        fun loadSongs()
        fun onSongSelected(song: Song)
        fun onPlayPauseClicked()
        fun onNextClicked()
        fun onPreviousClicked()
        fun onViewReady()
    }
}