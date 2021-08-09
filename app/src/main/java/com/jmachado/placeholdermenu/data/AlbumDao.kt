package com.jmachado.placeholdermenu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jmachado.placeholdermenu.model.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums_table ORDER BY userId")
    fun getAlbums(): Flow<List<Album>>

    @Query("SELECT * FROM albums_table WHERE id = :albumId")
    fun getAlbum(albumId: String): Flow<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Album>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Album)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll()
}