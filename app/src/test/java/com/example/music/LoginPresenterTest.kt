package com.example.music

import android.content.Context
import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginPresenterTest {

    @Mock
    private lateinit var view: LoginContract.View

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var presenter: LoginPresenter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(context.getSharedPreferences("music_app_prefs", Context.MODE_PRIVATE))
            .thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putBoolean(any(), any())).thenReturn(editor)
        `when`(editor.putString(any(), any())).thenReturn(editor)
        `when`(editor.remove(any())).thenReturn(editor)

        presenter = LoginPresenter(view, context)
    }

    @Test
    fun `login with valid credentials should succeed`() {
        // Given
        val username = "testuser"
        val password = "password123"

        // When
        presenter.login(username, password)

        // Then
        verify(view).showLoading(true)
        verify(view).showLoading(false)
        verify(view).showLoginSuccess()
        verify(view).navigateToMainActivity()
        verify(editor).putBoolean("is_logged_in", true)
        verify(editor).putString("username", username)
        verify(editor).apply()
    }

    @Test
    fun `login with empty username should show error`() {
        // Given
        val username = ""
        val password = "password123"

        // When
        presenter.login(username, password)

        // Then
        verify(view).showLoading(true)
        verify(view).showLoading(false)
        verify(view).showLoginError("Please enter username")
        verify(view, never()).showLoginSuccess()
        verify(view, never()).navigateToMainActivity()
    }

    @Test
    fun `login with short password should show error`() {
        // Given
        val username = "testuser"
        val password = "123"

        // When
        presenter.login(username, password)

        // Then
        verify(view).showLoading(true)
        verify(view).showLoading(false)
        verify(view).showLoginError("Password must be at least 4 characters")
        verify(view, never()).showLoginSuccess()
        verify(view, never()).navigateToMainActivity()
    }

    @Test
    fun `onViewReady when logged in should navigate to main`() {
        // Given
        `when`(sharedPreferences.getBoolean("is_logged_in", false)).thenReturn(true)

        // When
        presenter.onViewReady()

        // Then
        verify(view).navigateToMainActivity()
    }

    @Test
    fun `onViewReady when not logged in should not navigate`() {
        // Given
        `when`(sharedPreferences.getBoolean("is_logged_in", false)).thenReturn(false)

        // When
        presenter.onViewReady()

        // Then
        verify(view, never()).navigateToMainActivity()
    }

    @Test
    fun `logout should clear preferences`() {
        // When
        presenter.logout()

        // Then
        verify(editor).putBoolean("is_logged_in", false)
        verify(editor).remove("username")
        verify(editor).apply()
    }

    @Test
    fun `isLoggedIn should return correct status`() {
        // Given
        `when`(sharedPreferences.getBoolean("is_logged_in", false)).thenReturn(true)

        // When
        val result = presenter.isLoggedIn()

        // Then
        assert(result)
    }
}