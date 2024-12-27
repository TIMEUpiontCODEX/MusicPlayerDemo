package com.example.musicplayer.Model

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val coverUrl: String,
    val intro:String,
    val musicList: MutableList<Music>
)

class AlbumModel {
    suspend fun getAlbums(): List<Album> {
        // 模拟从网络或数据库中获取数据
        val musicList1 = mutableListOf<Music>()
        val musicList2 = mutableListOf<Music>()
        val musicList3 = mutableListOf<Music>()
        val musicList4 = mutableListOf<Music>()
        musicList1.add(Music(title = "Mojito"))
        musicList2.add(Music(title = "APT."))
        musicList3.add(Music(title = "Starting Up"))
        musicList3.add(Music(title = "I HATE MY PUTER -ALL MY ENEMIES ARE INSIDE IT-"))
        musicList3.add(Music(title = "Spectrum"))
        musicList3.add(Music(title = "Multi-Thread"))
        musicList3.add(Music(title = "Cloud Finding (feat. Kanata.N) [House Mix]"))
        musicList3.add(Music(title = "Bass Music Is Hard Yo"))
        musicList3.add(Music(title = "Dot to Dot (feat. Shully) [Rework]"))
        musicList3.add(Music(title = "ハードウェアは夢を見る"))
        musicList3.add(Music(title = "優しい嘘 (feat. ANNE)"))
        musicList3.add(Music(title = "やっぱり猫が好き"))
        musicList3.add(Music(title = "Wave (feat. 藍月なくる) [rejection remix]"))
        musicList3.add(Music(title = "Call My Name (feat. Yukacco) [MonarX Remix]"))
        musicList3.add(Music(title = "Fluffy (feat. 中村さんそ) [Yuigot Remix]"))
        musicList3.add(Music(title = "Midnight grow (feat. mami) [Blacklolita Remix]"))
        musicList4.add(Music(title = "纱布黄灿烽"))
        return listOf(
            Album("1", "Mojito", "周杰伦", "https://media.bjnews.com.cn/image/2020/06/18/4937754592927622129.jpeg?x-oss-process=image/resize,m_lfit,w_800","《迷迭香》拉丁慢舞风格再现——携手黄俊郎，\n" +
                    "以古巴鸡尾酒”调制”一曲浪漫。",musicList1),
            Album("2", "APT.", "ROSÉ/Bruno Mars", "https://tse4-mm.cn.bing.net/th/id/OIP-C.AWa-8CWe5lL2lM0pujTJAAHaFj?w=232&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7","",musicList2),
            Album("3", "BOOT LOADER", "Mameyudoufu", "https://p3fx.kgimg.com/stdmusic/240/20211110/20211110182507254086.jpg","genshin start",musicList3),
            Album("4", "刻晴", "sbhcf", "https://tse3-mm.cn.bing.net/th/id/OIP-C.N8yVKdxhNrmJJX83yfNNSQHaEK?w=249&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7","sbhcf",musicList4),
            Album("5", "USAO", "USAO", "https://tse3-mm.cn.bing.net/th/id/OIP-C.EaWxupO8wEEB49bN4NbfmwHaGA?w=223&h=181&c=7&r=0&o=5&dpr=1.1&pid=1.7","蛇精",musicList4),
            // ...
        )
    }
     suspend fun getAlbumById(id: String): Album? {
        return getAlbums().find { it.id == id }
    }
}