package com.example.musicplayer.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.LocalNavHostController
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Music() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController= LocalNavHostController.current
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column {
                    Text("用户名", modifier = Modifier.padding(16.dp))
                    NavigationDrawerItem(
                        label = { Text("主页") },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            // 执行点击事件
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("设置") },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            // 执行点击事件
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("登录") },
                        icon = { Icon(Icons.Default.Face, contentDescription = "Login") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("login")
                        }
                    )
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("主页") },//用户名
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "返回")
                        }
                    },
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color.White,
                ) {
//                    Row(
//                        modifier = Modifier.padding(start = 15.dp, end = 3.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.bootloader),
//                            contentDescription = "歌曲封面",
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .size(50.dp)
//                                .clip(CircleShape)
//                        )
//                        Text(
//                            "I HATE MY PUTER -ALL MY ENEMIES ARE INSIDE IT-",
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            modifier = Modifier
//                                .width(190.dp)
//                                .padding(10.dp),
//                            fontSize = 17.sp
//                        )
//                        IconButton(
//                            onClick = { /* TODO: 处理播放操作 */ },
//                            modifier = Modifier.padding(8.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.PlayArrow,
//                                contentDescription = "播放",
//                                tint = Color.Black
//                            )
//                        }
//                        IconButton(
//                            onClick = { /* TODO: 处理播放操作 */ },
//                            modifier = Modifier.padding(8.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Menu,
//                                contentDescription = "当前播放",
//                                tint = Color.Black
//                            )
//                        }
//                    }
                    Row (
                        modifier = Modifier.fillMaxWidth() ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    )
                    {
                        IconButton(
                           onClick = { /* TODO: 处理播放操作 */ },
                           modifier = Modifier.padding(10.dp)
                       ) {
                           Icon(
                               imageVector = Icons.Filled.Home,
                               contentDescription = "主页",
                               modifier = Modifier.fillMaxSize(),
                               tint = Color.Magenta
                           )
                       }
                        IconButton(
                            onClick = { /* TODO: 处理搜索操作 */ },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "搜索",
                                modifier = Modifier.fillMaxSize(),
                                tint = Color.Black
                            )
                        }
                        IconButton(
                            onClick = { /* TODO: 处理歌单操作 */ },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "歌单",
                                modifier = Modifier.fillMaxSize(),
                                tint = Color.Black
                            )
                        }
                        IconButton(
                            onClick = { navController.navigate("user") },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "用户",
                                modifier = Modifier.fillMaxSize(),
                                tint = Color.Black
                            )
                        }
                    }
                }
            },
        ) { innerPadding ->
            MainContent(Modifier.padding(innerPadding))
            PlayerCard(Modifier.padding(top = 375.dp))
        }
    }
}

@Composable
fun MainContent(modifier: Modifier) {

}

@Preview
@Composable
fun MusicCardPreview() {
    Music()
}
