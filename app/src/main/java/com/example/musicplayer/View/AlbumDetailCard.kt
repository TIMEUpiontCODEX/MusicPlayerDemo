package com.example.musicplayer.View

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.musicplayer.LocalExoPlayer
import com.example.musicplayer.Model.AlbumModel
import com.example.musicplayer.Model.UserModel
import com.example.musicplayer.ViewModel.PlayerViewModel
import com.example.musicplayer.ViewModel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(albumId: String,userviewModel: UserViewModel = viewModel(),playerviewmodel:PlayerViewModel= viewModel()) {
    val navController = LocalNavHostController.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val albumModel = AlbumModel()
    val player = LocalExoPlayer.current
    val albumState = remember { mutableStateOf<Album?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        albumState.value = albumModel.getAlbumById(albumId)
    }
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
                                Row(modifier = Modifier.fillMaxWidth().clickable(onClick = {
                                    Log.d("DEBUG", "点击开始")
                                    userviewModel.uiState.value.user?.let {
                                        coroutineScope.launch {
                                            playerviewmodel.downloadSong(
                                                music.musicid,
                                                it.User_token, context
                                            )
                                        }
                                    }?:run {
                                        Log.d("AUTH","no user")
                                    }
                                }).background(Color.LightGray)) {
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
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("当前播放") },
            text = {
                player?.let {
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