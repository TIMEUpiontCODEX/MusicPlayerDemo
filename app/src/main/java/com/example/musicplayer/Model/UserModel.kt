package com.example.musicplayer.Model

data class User(
    val User_id:String,
    val User_token:String,
    val UserImageUrl:String
    )

class UserModel {
    suspend fun getUser():User{
        return User("imp","114514","https://qlogo4.store.qq.com/qzone/3298249487/3298249487/100?1733382242")
    }
}