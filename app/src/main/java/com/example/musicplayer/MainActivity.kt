package com.example.musicplayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.View.Music
import com.example.musicplayer.View.User

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HostController()
        }
    }
}

object RouteConfig {
    const val ROUTE_USER = "user"
    const val ROUTE_MENU = "menu"
    const val ROUTE_SUGGEST = "suggest"
}

@Composable
fun HostController(){
    val navController= rememberNavController()
    NavHost(navController=navController, startDestination = RouteConfig.ROUTE_MENU){
        composable(RouteConfig.ROUTE_USER) { User(navController) }
        composable (RouteConfig.ROUTE_MENU){  Music(navController) }
    }
}

@Preview
@Composable
fun HostPreview(){
    HostController()
}