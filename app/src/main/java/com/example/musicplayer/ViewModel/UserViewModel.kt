package com.example.musicplayer.ViewModel
import com.example.musicplayer.Model.AlbumModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.Model.Album
import com.example.musicplayer.Model.User
import com.example.musicplayer.Model.UserModel
import com.example.musicplayer.UiState.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val album: AlbumModel,private val user:UserModel) : ViewModel() {
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    private val _users = MutableStateFlow<User?>(null)
//    private val _uiState = MutableStateFlow(UserUiState())
//    val uiState: StateFlow<UserUiState> get() = _uiState
    val albums: StateFlow<List<Album>> = _albums
    val users: StateFlow<User?> = _users
    fun loadAlbums() {
        viewModelScope.launch {
            val albums = album.getAlbums()
            _albums.value = albums
        }
    }
    fun loadUser() {
        viewModelScope.launch {
            val users = user.getUser()
            _users.value = users
        }
    }
//    fun updateUserName(newName:String){
//        _uiState.value = _uiState.value.copy(UserName = newName)
//    }
//    fun updateUserToken(newToken:String){
//        _uiState.value = _uiState.value.copy(UserToken = newToken)
//    }
}