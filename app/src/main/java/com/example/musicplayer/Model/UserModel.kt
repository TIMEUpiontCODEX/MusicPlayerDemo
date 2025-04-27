package com.example.musicplayer.Model

import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException

data class User(
    val User_id:String="立即登录",
    val User_token:String="",
    val UserImageUrl:String="",
    val isPremium:Boolean=false
    ){
    companion object {
        val EMPTY = User() // 空对象单例
    }
}

class UserModel {
    suspend fun getUser():User{
     //   return User("imp","114514","https://qlogo4.store.qq.com/qzone/3298249487/3298249487/100?1733382242")
        return User("矮鬼","114514","https://qlogo4.store.qq.com/qzone/3124788399/3124788399/100?1733147540")

    }
}
interface UserRepository {
    suspend fun login(username: String, password: String): User
    suspend fun logout()
    suspend fun register(username: String, password: String,email:String):User
}
class UserRepositoryImpl(
    private val baseUrl: String = "http://0.0.0.0:8000/catalog/api/"
) : UserRepository {
    // 内部API接口定义
    private interface AuthApiService {
        @POST("register/")
        suspend fun register(@Body request: RegisterRequest): Response<User>
        @POST("token/")  // JWT登录端点
        suspend fun login(@Body request: LoginRequest): Response<TokenResponse>
    }
    private data class TokenResponse(
        @SerializedName("access") val accessToken: String
    )
    // 统一请求数据结构
    private data class LoginRequest(
        val username: String,
        val password: String
    )
    private data class RegisterRequest(
        val username: String,
        val password: String,
        val email: String
    )
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }
    override suspend fun login(username: String, password: String): User {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("UserRepository", "开始登录用户: $username")

                val response = retrofit.login(
                    LoginRequest(
                        username = username,
                        password = password
                    )
                )

                if (response.isSuccessful) {
                    response.body()?.let { tokenResponse ->
                        User(
                            User_id = username,
                            User_token = tokenResponse.accessToken
                        ).also {
                            Log.i("UserRepository", "登录成功: ${it.User_id}")
                        }
                    } ?: throw IllegalStateException("响应体为空")
                } else {
                    val error = response.errorBody()?.string() ?: "未知错误"
                    Log.e("UserRepository", "登录失败: ${response.code()} - $error")
                    throw HttpException(response)
                }
            } catch (e: HttpException) {
                Log.e("UserRepository", "HTTP错误: ${e.code()}", e)
                throw when (e.code()) {
                    400 -> IllegalArgumentException("用户名或密码错误")
                    401 -> IllegalArgumentException("认证失败")
                    else -> e
                }
            } catch (e: Exception) {
                Log.e("UserRepository", "登录异常", e)
                throw when (e) {
                    is IOException -> IOException("网络连接失败，请检查网络设置", e)
                    else -> Exception("登录失败: ${e.message}", e)
                }
            }
        }
    }
    override suspend fun logout() {}
    override suspend fun register(username: String, password: String, email: String): User {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("UserRepository", "开始注册用户: $username")

                val response = retrofit.register(
                    RegisterRequest(
                        username = username,
                        password = password,
                        email = email
                    )
                )
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        Log.i("UserRepository", "注册成功: ${user.User_id}")
                        user
                    } ?: throw IllegalStateException("响应体为空")
                } else {
                    val error = response.errorBody()?.string() ?: "未知错误"
                    Log.e("UserRepository", "注册失败: ${response.code()} - $error")
                    throw HttpException(response)
                }
            } catch (e: HttpException) {
                Log.e("UserRepository", "HTTP错误: ${e.code()}", e)
                throw when (e.code()) {
                    400 -> IllegalArgumentException("无效的请求参数")
                    409 -> IllegalArgumentException("用户名已存在")
                    else -> e
                }
            } catch (e: Exception) {
                Log.e("UserRepository", "注册异常", e)
                throw when (e) {
                    is IOException -> IOException("网络连接失败，请检查网络设置", e)
                    else -> Exception("注册失败: ${e.message}", e)
                }
            }
        }
    }
}