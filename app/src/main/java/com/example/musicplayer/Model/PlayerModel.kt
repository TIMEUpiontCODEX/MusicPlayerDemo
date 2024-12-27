package com.example.musicplayer.Model

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer

class PlayerModel(context: Context) {
    // 创建 ExoPlayer 实例
    val player: ExoPlayer = ExoPlayer.Builder(context).build()
}