package com.example.musicplayer.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.musicplayer.LocalNavHostController
import com.example.musicplayer.Model.Album
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.style.TextOverflow
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.musicplayer.LocalExoPlayer
import com.example.musicplayer.Model.AlbumModel
import com.example.musicplayer.Model.UserModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(albumId: String) {
    val navController = LocalNavHostController.current
    val albumModel = AlbumModel()
    val player = LocalExoPlayer.current
    val albumState = remember { mutableStateOf<Album?>(null) }
    LaunchedEffect(Unit) {
        albumState.value = albumModel.getAlbumById(albumId)
    }
    val user = UserModel()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent, tonalElevation = 10.dp) {
                PlayerCard(modifier = Modifier)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val album = albumState.value
                if (album != null) {
                    Card(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .size(width = 250.dp, height = 250.dp)
                            .clickable {
                            },
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        )
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(album.coverUrl),
                            contentDescription = "Album Cover",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // 显示专辑标题
                    Text(
                        text = album.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    // 显示艺术家名称（红色）
                    Text(
                        text = album.artist,
                        color = Color.Red,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = album.intro,
                        color = Color.Gray.copy(0.7f),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp),
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(0.dp) // 设置行距
                    ) {
                        itemsIndexed(album.musicList) { index, music ->
                            val MediaItem= MediaItem.Builder().setUri(music.musicurl).setMediaMetadata(
                                    MediaMetadata.Builder()
                                        .setTitle(music.title) // 设置标题
                                        .build()
                                    ).build()
                            Column() {
                                // 显示序号和音乐标题
                                if (index <= album.musicList.lastIndex || index == 0) {
                                    HorizontalDivider(
                                        color = Color.Gray.copy(0.5f),
                                        thickness = 1.dp,
                                        modifier = Modifier.padding(
                                            start = 20.dp,
                                            top = 15.dp,
                                            end = 20.dp
                                        )
                                    ) // 分隔线
                                }
                                Row(modifier = Modifier.clickable(onClick = {
                                        player.addMediaItem(MediaItem)
                                    player.play()
                                })) {
                                    Text(
                                        text = " ${index + 1}",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(
                                            start = 20.dp,
                                            top = 15.dp,
                                            end = 10.dp
                                        ),
                                        color = Color.Gray.copy(0.7f)
                                    )
                                    Text(
                                        text = " ${music.title}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .widthIn(max = 250.dp)
                                            .padding(top = 15.dp)
                                            .clickable { }
                                    )
                                }
                                if (index == album.musicList.lastIndex) {
                                    HorizontalDivider(
                                        color = Color.Gray.copy(0.5f),
                                        thickness = 1.dp,
                                        modifier = Modifier.padding(
                                            start = 20.dp,
                                            top = 15.dp,
                                            end = 20.dp
                                        )
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // 可以显示一个加载指示器或占位符
                    CircularProgressIndicator()
                }

            }

        }
    }
}