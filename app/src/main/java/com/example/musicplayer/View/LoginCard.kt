package com.example.musicplayer.View

import RegisterScreen
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicplayer.LocalNavHostController
import com.example.musicplayer.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(viewModel: UserViewModel = viewModel()) {
    val navController = LocalNavHostController.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("登录页面") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
            )
        }
    ){
            innerPadding -> LoginContent(Modifier.padding(innerPadding),viewModel)
    }
}

//
//@Composable
//fun LoginContent(modifier: Modifier,viewModel: UserViewModel = viewModel()) {
//    var username by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    val context = LocalContext.current
//    val navController= LocalNavHostController.current
//    Column(
//        modifier = modifier.padding(top = 200.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // 用户名输入字段
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = "用户名")
//            TextField(
//                value = username,
//                onValueChange = { str -> username = str },
//                singleLine = true,
//                modifier = Modifier.weight(1f)
//            )
//        }
//
//        // 密码输入字段
//        Spacer(modifier = Modifier.height(16.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = "密码")
//            TextField(
//                value = password,
//                onValueChange = { str -> password = str },
//                singleLine = true,
//                visualTransformation = PasswordVisualTransformation(),
//                modifier = Modifier.weight(1f)
//            )
//        }
//
//        // 登录按钮
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            modifier = Modifier.fillMaxWidth(),
//            onClick = {
//                viewModel.login(username,password)
//            }
//        ) {
//            Text("登录", color = Color.White)
//        }
//
//        // 快捷登录和忘记密码链接
//        Spacer(modifier = Modifier.height(8.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                text = "注册", color = Color.Gray,
//                modifier=Modifier.clickable(
//                    onClick = {
//                        navController.navigate("register")
//                    }
//                )
//            )
//            Text(text = "忘记密码", color = Color.Gray)
//        }
//    }
//}
 @Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val navController = LocalNavHostController.current

    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 标题
        Text(
            text = "用户登录",
            modifier = Modifier.padding(bottom = 40.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // 用户名输入
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("用户名") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // 密码输入
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("密码") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 登录按钮
        Button(
            onClick = { viewModel.login(username, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("登录")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 底部链接
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "注册账号",
                modifier = Modifier.clickable {
                    navController.navigate("register")
                },
                color = Color.Blue
            )
            Text(
                text = "忘记密码",
                color = Color.Gray
            )
        }
    }
}


@Preview
@Composable
fun PreviewLogin() {
    Login()
}
