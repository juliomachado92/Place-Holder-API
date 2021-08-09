package com.jmachado.placeholdermenu.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jmachado.placeholdermenu.model.Todos
import kotlinx.coroutines.flow.Flow

@Dao
interface TodosDao {
    @Query("SELECT * FROM todos_table ORDER BY userId")
    fun getTodos(): Flow<List<Todos>>

    @Query("SELECT * FROM todos_table WHERE id = :albumId")
    fun getTodo(albumId: String): Flow<Todos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Todos>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Todos)

    @Query("DELETE FROM todos_table")
    suspend fun deleteAll()
}