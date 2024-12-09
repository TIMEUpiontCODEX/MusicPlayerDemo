package com.example.musicplayer.ComposableMusicCard
import androidx.annotation.DrawableRes


data class Music(
    @DrawableRes val imageResource: Int,
    val title: String="Music Title",
    val author: String="default"
)