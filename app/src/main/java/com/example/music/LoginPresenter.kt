package com.example.music

import android.content.Context
import android.content.SharedPreferences

class LoginPresenter(
    private val view: LoginContract.View,
    private val context: Context
) : LoginContract.Presenter {

    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("music_app_prefs", Context.MODE_PRIVATE)

    override fun login(username: String, password: String) {
        view.showLoading(true)

        // Simple validation
        when {
            username.isBlank() -> {
                view.showLoading(false)
                view.showLoginError("Please enter username")
                return
            }
            password.isBlank() -> {
                view.showLoading(false)
                view.showLoginError("Please enter password")
                return
            }
            password.length < 4 -> {
                view.showLoading(false)
                view.showLoginError("Password must be at least 4 characters")
                return
            }
        }

        // Simulate login process - in real app this would be network call
        // For demo purposes, accept any valid username/password combination
        if (username.isNotEmpty() && password.length >= 4) {
            // Save login state
            sharedPreferences.edit()
                .putBoolean("is_logged_in", true)
                .putString("username", username)
                .apply()

            view.showLoading(false)
            view.showLoginSuccess()
            view.navigateToMainActivity()
        } else {
            view.showLoading(false)
            view.showLoginError("Invalid credentials")
        }
    }

    override fun onViewReady() {
        // Check if user is already logged in
        if (sharedPreferences.getBoolean("is_logged_in", false)) {
            view.navigateToMainActivity()
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    fun logout() {
        sharedPreferences.edit()
            .putBoolean("is_logged_in", false)
            .remove("username")
            .apply()
    }
}