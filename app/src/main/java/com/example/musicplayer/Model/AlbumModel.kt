package com.example.musicplayer.Model

data class Album(
    val id: Int,
    val title: String,
    val artist: String,
    val coverUrl: String
)

class AlbumModel {
    suspend fun getAlbums(): List<Album> {
        // 模拟从网络或数据库中获取数据
        return listOf(
            Album(1, "Mojito", "周杰伦", "https://media.bjnews.com.cn/image/2020/06/18/4937754592927622129.jpeg?x-oss-process=image/resize,m_lfit,w_800"),
            Album(2, "APT.", "ROSÉ/Bruno Mars", "https://tse4-mm.cn.bing.net/th/id/OIP-C.AWa-8CWe5lL2lM0pujTJAAHaFj?w=232&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7"),
            Album(3, "钟离", "sbhcf", "https://tse3-mm.cn.bing.net/th/id/OIP-C.DbwjqVSpLq6p_ApeEdLHEAHaNK?w=115&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7"),
            Album(4, "刻晴", "sbhcf", "https://tse3-mm.cn.bing.net/th/id/OIP-C.N8yVKdxhNrmJJX83yfNNSQHaEK?w=249&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7"),
            Album(5, "USAO", "USAO", "https://tse3-mm.cn.bing.net/th/id/OIP-C.EaWxupO8wEEB49bN4NbfmwHaGA?w=223&h=181&c=7&r=0&o=5&dpr=1.1&pid=1.7"),
            Album(6, "Album 6", "Artist 2", "https://tse1-mm.cn.bing.net/th/id/OIP-C.2-NLKlUQwVjRjDjFaqj8rAHaHa?w=160&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7"),
            Album(7, "Album 7", "Artist 1", "https://media.bjnews.com.cn/image/2020/06/18/4937754592927622129.jpeg?x-oss-process=image/resize,m_lfit,w_800"),
            Album(8, "Album 8", "Artist 2", "https://tse4-mm.cn.bing.net/th/id/OIP-C.AWa-8CWe5lL2lM0pujTJAAHaFj?w=232&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7"),
            // ...
        )
    }
}