package com.example.music

class MusicPlayerPresenter(
    private val view: MusicPlayerContract.View,
    private val model: MusicPlayerModel
) : MusicPlayerContract.Presenter {

    override fun loadSongs() {
        val songs = model.getSongs()
        view.showSongs(songs)
    }

    override fun onSongSelected(song: Song) {
        model.play(song)
        view.updateMiniPlayer(song, true)
        view.updateNotification(song, true)
    }

    override fun onPlayPauseClicked() {
        val currentSong = model.getCurrentSong() ?: return
        if (model.isPlaying()) {
            model.pause()
            view.updateMiniPlayer(currentSong, false)
            view.updateNotification(currentSong, false)
        } else {
            model.play(currentSong)
            view.updateMiniPlayer(currentSong, true)
            view.updateNotification(currentSong, true)
        }
    }

    override fun onNextClicked() {
        model.next()
        model.getCurrentSong()?.let {
            view.updateMiniPlayer(it, true)
            view.updateNotification(it, true)
        }
    }

    override fun onPreviousClicked() {
        model.previous()
        model.getCurrentSong()?.let {
            view.updateMiniPlayer(it, true)
            view.updateNotification(it, true)
        }
    }

    override fun onViewReady() {
        loadSongs()
        model.getCurrentSong()?.let {
            view.updateMiniPlayer(it, model.isPlaying())
        }
    }
}