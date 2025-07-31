package com.example.music

interface LoginContract {
    interface View {
        fun showLoginSuccess()
        fun showLoginError(message: String)
        fun showLoading(isLoading: Boolean)
        fun navigateToMainActivity()
    }

    interface Presenter {
        fun login(username: String, password: String)
        fun onViewReady()
    }
}