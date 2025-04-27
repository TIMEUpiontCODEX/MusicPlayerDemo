package com.example.musicplayer.UiState

import com.example.musicplayer.Model.User

data class UserUiState(
    val user: User? = null,
    val isLoggedIn: Boolean = false,
    val isPremium: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)