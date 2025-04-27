package com.example.musicplayer.ViewModel
import com.example.musicplayer.Model.AlbumModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.Model.Album
import com.example.musicplayer.Model.User
import com.example.musicplayer.Model.UserModel
import com.example.musicplayer.Model.UserRepository
import com.example.musicplayer.Model.UserRepositoryImpl
import com.example.musicplayer.UiState.UserUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
 class UserViewModel(private val album: AlbumModel,private val userRepository: UserRepositoryImpl) : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    private val _events = MutableSharedFlow<UiEvent>()
    val events: SharedFlow<UiEvent> = _events

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
        object NavigateToHome : UiEvent()
    }
fun login(username: String, password: String) {
    viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        try {
            val user = userRepository.login(username, password)

            _uiState.update {
                it.copy(
                    user = user,
                    isLoggedIn = true,
                    isPremium = user.isPremium,
                    isLoading = false
                )
            }

            // 触发一次性事件
            _events.emit(UiEvent.NavigateToHome)

        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = e.message ?: "Login failed"
                )
            }
            _events.emit(UiEvent.ShowToast("Login failed: ${e.message}"))
        }
    }
}
 fun register(username: String, password: String,email:String) {
     viewModelScope.launch {
         try {
             val user = userRepository.register(username, password,email)

             _events.emit(UiEvent.NavigateToHome)

         } catch (e: Exception) {
             _events.emit(UiEvent.ShowToast("register failed: ${e.message}"))
         }
     }
 }

fun logout() {
    viewModelScope.launch {
        userRepository.logout()
        _uiState.update {
            UserUiState() // 重置状态
        }
    }
}
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
//    private val _users = MutableStateFlow<User?>(null)
    val albums: StateFlow<List<Album>> = _albums
//    val users: StateFlow<User?> = _users
    fun loadAlbums() {
        viewModelScope.launch {
            val albums = album.getAlbums()
            _albums.value = albums
        }
    }
//    fun loadUser() {
//        viewModelScope.launch {
//            val users = user.getUser()
//            _users.value = users
//        }
//    }
//    fun updateUserName(newName:String){
//        _uiState.value = _uiState.value.copy(UserName = newName)
//    }
//    fun updateUserToken(newToken:String){
//        _uiState.value = _uiState.value.copy(UserToken = newToken)
//    }
}