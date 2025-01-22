package com.example.musicplayer.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicplayer.LocalExoPlayer
import com.example.musicplayer.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
@Composable
fun PlayerCard(modifier: Modifier) {


    // 使用 CompositionLocalProvider 提供 ExoPlayer 实例
        val cplayer = LocalExoPlayer.current
        // 获取当前媒体项信息
        val currentMediaItem = cplayer.currentMediaItem
        var showDialog by remember { mutableStateOf(false) }
        val title = currentMediaItem?.mediaMetadata?.title.toString()
        val artist = currentMediaItem?.mediaMetadata?.artist.toString()
        val duration = cplayer.duration
        val currentPosition = cplayer.currentPosition

        Row(
            modifier = modifier
                .padding( start = 25.dp, end = 3.dp, bottom = 0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.bootloader),
                contentDescription = "歌曲封面",
                contentScale = ContentScale.Crop,
                modifier = modifier.size(50.dp).clip(CircleShape)
            )
            Text(
                text = title.takeIf { it.isNotEmpty() } ?: "等待播放中",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.width(190.dp).padding(10.dp),
                fontSize = 17.sp
            )
            IconButton(
                onClick = { cplayer.play() },
                modifier = modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "播放",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { showDialog = true },
                modifier = modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "当前播放",
                    tint = Color.Black
                )
            }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("当前播放") },
            text = {
                currentMediaItem?.let {
                    Text("标题: ${it.mediaMetadata.title}\n\n")
                } ?: Text("没有正在播放的音乐")
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("关闭")
                }
            }
        )
    }
}

