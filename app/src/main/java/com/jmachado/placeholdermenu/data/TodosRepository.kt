package com.jmachado.placeholdermenu.data

import androidx.annotation.WorkerThread
import com.jmachado.placeholdermenu.model.Todos
import kotlinx.coroutines.flow.Flow

class TodosRepository (private val todosDao: TodosDao){

    val allTodos: Flow<List<Todos>> = todosDao.getTodos()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item: Todos) {
        todosDao.insert(item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(list: List<Todos>){
        todosDao.insertAll(list)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll(){
        todosDao.deleteAll()
    }
}