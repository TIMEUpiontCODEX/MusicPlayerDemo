package com.example.musicplayer
import RegisterScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
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
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicplayer.Model.AlbumModel
import com.example.musicplayer.Model.PlayerModel
import com.example.musicplayer.Model.UserRepositoryImpl
import com.example.musicplayer.View.AlbumDetailScreen
import com.example.musicplayer.View.Login
import com.example.musicplayer.View.Music
import com.example.musicplayer.View.User
import com.example.musicplayer.ViewModel.PlayerViewModel
import com.example.musicplayer.ViewModel.UserViewModel

val LocalExoPlayer = compositionLocalOf<ExoPlayer> { error("No ExoPlayer provided") }
class MainActivity : ComponentActivity() {
    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val playerModel = remember { PlayerModel(context) }
            CompositionLocalProvider(LocalExoPlayer provides playerModel.player) {
                HostController(modifier = Modifier.zIndex(1f))
            }
            }
        }
}

object RouteConfig {
    const val ROUTE_USER = "user"
    const val ROUTE_MENU = "menu"
    const val ROUTE_SUGGEST = "suggest"
    const val ROUTE_LOGIN="login"
    const val ROUTE_REGISTER="register"
}

val LocalNavHostController = staticCompositionLocalOf<NavHostController> { error("No NavHostController provided") }
@Composable
fun HostController(modifier: Modifier){
    val navController= rememberNavController()
    val album= AlbumModel()
    val userre= UserRepositoryImpl()
    val userViewModel = UserViewModel(album,userre)
    val playerViewModel=PlayerViewModel()

    CompositionLocalProvider(LocalNavHostController provides navController) {
        NavHost(navController = navController, startDestination = RouteConfig.ROUTE_MENU) {
            composable(RouteConfig.ROUTE_USER) { User(userViewModel) }
            composable(RouteConfig.ROUTE_MENU) { Music() }
            composable(RouteConfig.ROUTE_LOGIN) { Login(userViewModel) }
            composable(RouteConfig.ROUTE_REGISTER) { RegisterScreen(userViewModel) }
            composable(
                route = "albumDetail/{albumId}",
                arguments = listOf(navArgument("albumId") { type = NavType.StringType })
            ) { backStackEntry ->
                val albumId = backStackEntry.arguments?.getString("albumId")
                albumId?.let {
                    AlbumDetailScreen(albumId = it,userViewModel,playerViewModel)
                }
            }
        }
    }
}

@Preview
@Composable
fun HostPreview(){
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }
    Box(modifier = Modifier.fillMaxSize()) {
        HostController(modifier = Modifier.zIndex(1f))
        Row(
            modifier = Modifier.zIndex(2f)
                .padding(top = 100.dp) // 根据需要调整padding
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
                modifier = Modifier
                    .width(190.dp)
                    .padding(10.dp),
                fontSize = 17.sp
            )
            IconButton(
                onClick = { player.play() },
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
}
