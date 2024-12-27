package com.example.musicplayer.View

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("登录页面") },
                actions = {
                    // 可以添加其他操作，如注册按钮
                    IconButton(onClick = { /* 注册操作 */ }) {
                        Icon(Icons.Default.Add, contentDescription = "注册")
                    }
                }
            )
        }
    ){
            innerPadding -> LoginContent(Modifier.padding(innerPadding))
    }
}


@Composable
fun LoginContent(modifier: Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(top = 200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 用户名输入字段
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "用户名")
            TextField(
                value = username,
                onValueChange = { str -> username = str },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        // 密码输入字段
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "密码")
            TextField(
                value = password,
                onValueChange = { str -> password = str },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.weight(1f)
            )
        }

        // 登录按钮
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (username == "test" && password == "123") {
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("登录", color = Color.White)
        }

        // 快捷登录和忘记密码链接
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "快捷登录", color = Color.Gray)
            Text(text = "忘记密码", color = Color.Gray)
        }
    }
}

@Preview
@Composable
fun PreviewLogin() {
    Login()
}
