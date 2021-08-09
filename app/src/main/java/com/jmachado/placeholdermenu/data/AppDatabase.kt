package com.jmachado.placeholdermenu.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jmachado.placeholdermenu.DATABASE_NAME
import com.jmachado.placeholdermenu.model.Album
import com.jmachado.placeholdermenu.model.Post
import com.jmachado.placeholdermenu.model.Todos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ Post::class, Album::class, Todos::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun postDao(): PostDao
    abstract fun albumDao(): AlbumDao
    abstract fun todosDao(): TodosDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    //Populate database
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                this.INSTANCE = instance
                instance
            }
        }
    }
}
