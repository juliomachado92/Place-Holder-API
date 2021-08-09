package com.jmachado.placeholdermenu.data

import androidx.annotation.WorkerThread
import com.jmachado.placeholdermenu.model.Post
import kotlinx.coroutines.flow.Flow


class PostRepository(private val postDao: PostDao) {

    val allPosts: Flow<List<Post>> = postDao.getPosts()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item: Post) {
        postDao.insert(item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(list: List<Post>){
        postDao.insertAll(list)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll(){
        postDao.deleteAll()
    }

}