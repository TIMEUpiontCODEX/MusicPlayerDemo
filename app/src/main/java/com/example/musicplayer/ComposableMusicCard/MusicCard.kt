package com.example.musicplayer.ComposableMusicCard
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.musicplayer.R
import java.io.File


class MusicCard {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Music(){
    //后面导入用户数据
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("主页") },//用户名
                navigationIcon = {
                    IconButton(onClick = { /* TODO: 导航操作 */ }) {
                        Icon(imageVector =Icons.Filled.Menu, contentDescription = "返回")
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar (
                containerColor = Color.White,
            ) {
                Row(
                    modifier = Modifier.padding(start=15.dp,end=3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bootloader),
                        contentDescription = "歌曲封面",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        "I HATE MY PUTER -ALL MY ENEMIES ARE INSIDE IT-",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(190.dp).padding(10.dp),
                        fontSize = 17.sp
                    )
                    IconButton(
                        onClick = { /* TODO: 处理播放操作 */ },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "播放",
                            tint = Color.Black
                        )
                    }
                    IconButton(
                        onClick = { /* TODO: 处理播放操作 */ },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "当前播放",
                            tint = Color.Black
                        )
                    }
                }
            }
        },
    ){
            innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}


@Composable
fun BodyContent(modifier: Modifier) {
}
@Preview
@Composable
fun MusicCardPreview()
{
    Music()
}
