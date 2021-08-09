package com.jmachado.placeholdermenu.data

import androidx.annotation.WorkerThread
import com.jmachado.placeholdermenu.model.Album
import kotlinx.coroutines.flow.Flow

class AlbumRepository(private val albumDao: AlbumDao) {

    val allAlbums: Flow<List<Album>> = albumDao.getAlbums()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item: Album) {
        albumDao.insert(item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(list: List<Album>){
        albumDao.insertAll(list)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll(){
        albumDao.deleteAll()
    }
}