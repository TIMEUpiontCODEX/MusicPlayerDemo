package com.example.musicplayer.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.Model.Album
import com.example.musicplayer.R
import com.example.musicplayer.ViewModel.UserViewModel
import kotlinx.coroutines.launch
import coil.compose.rememberAsyncImagePainter
import com.example.musicplayer.LocalNavHostController
import com.example.musicplayer.Model.AlbumModel
import com.example.musicplayer.Model.UserModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun User(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController=LocalNavHostController.current
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
                    // 添加其他菜单项或内容
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
                    Row (
                        modifier = Modifier.fillMaxWidth() ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    )
                    {
                        IconButton(
                            onClick = { navController.navigate("menu") },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "主页",
                                modifier = Modifier.fillMaxSize(),
                                tint = Color.Black
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
                            onClick = { /* TODO: 处理用户操作 */ },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "用户",
                                modifier = Modifier.fillMaxSize(),
                                tint = Color.Magenta
                            )
                        }
                    }
                }
            },
        ) {  innerPadding ->
            val album=AlbumModel()
            val user=UserModel()
            val userViewModel = UserViewModel(album,user)
            UserContent(modifier = Modifier.padding(innerPadding),userViewModel)
            PlayerCard(modifier = Modifier.padding(top = 375.dp))
        }
    }
}


@Composable
fun AlbumCard(album: Album) {
    val navController=LocalNavHostController.current
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.clickable {
            // 导航到详情页面，并传递专辑ID
            navController.navigate("albumDetail/${album.id}")
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 10.dp)
                .height(150.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(album.coverUrl),
                contentDescription = "Album Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = album.title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 0.dp)

        )
        Text(
            text = album.artist,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp))
    }
}

@Composable
fun UserContent(modifier: Modifier,viewModel: UserViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.loadAlbums()
        viewModel.loadUser()
    }
    val albums by viewModel.albums.collectAsState(emptyList())
    val users by viewModel.users.collectAsState(null)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp) // 添加额外的内边距
            .systemBarsPadding(),// 确保内容不会被状态栏和导航栏遮挡
        horizontalAlignment = Alignment.CenterHorizontally, // 水平居中
    ) {
        Image(
            painter = rememberAsyncImagePainter(users?.UserImageUrl),
            contentDescription = "用户头像",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 10.dp)
                .size(80.dp)
                .clip(CircleShape)
        )
        Row(
            modifier = Modifier.padding(top = 10.dp),
        ) {
            Text(
                users?.User_id?:"未命名用户",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 10.dp)

            )
            Image(
                painter = painterResource(id = R.drawable.vip),
                contentDescription = "vip标识",
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 5.dp)
            )
        }
        Row(
            modifier = Modifier
                .height(80.dp)
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta.copy(alpha = 0.3f),
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(width = 90.dp, height = 40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "最爱歌曲",
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "喜欢",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta.copy(alpha = 0.3f),
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(width = 90.dp, height = 40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "本地",
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "本地",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta.copy(alpha = 0.3f),
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(width = 90.dp, height = 40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "信息",
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "信息",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }

            }
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta.copy(alpha = 0.4f),
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(width = 40.dp, height = 40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "其它应用",
                )
            }
        }
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(albums) { album ->
                    AlbumCard(album = album)
                }
            }
        }
    }
}

@Preview
@Composable
fun UserPreview(){
}