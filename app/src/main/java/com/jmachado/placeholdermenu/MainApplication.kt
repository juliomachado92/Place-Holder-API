package com.jmachado.placeholdermenu

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.jmachado.placeholdermenu.data.AlbumRepository
import com.jmachado.placeholdermenu.data.AppDatabase
import com.jmachado.placeholdermenu.data.PostRepository
import com.jmachado.placeholdermenu.data.TodosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this,applicationScope) }
    val postRepository by lazy { PostRepository(database.postDao()) }
    val albumRepository by lazy { AlbumRepository(database.albumDao()) }
    val todosRepository by lazy { TodosRepository(database.todosDao()) }

    fun isConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}