package com.jmachado.placeholdermenu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jmachado.placeholdermenu.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts_table ORDER BY userId")
    fun getPosts(): Flow<List<Post>>

    @Query("SELECT * FROM posts_table WHERE id = :postId")
    fun getPost(postId: String): Flow<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(post: Post)

    @Query("DELETE FROM posts_table")
    suspend fun deleteAll()
}