package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun getDao(): ArticlesDao

    companion object {
        @Volatile
        var INSTANCE: ArticleDataBase? = null
//        private val LOCK = Any()
//        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
//            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
//        }

        fun createDatabase(context: Context): ArticleDataBase {
            var instance = INSTANCE
            if (instance != null) {
                return instance
            } else {
                instance =
                    Room.databaseBuilder(context, ArticleDataBase::class.java, "articles_database")
                        .build()
            }
            INSTANCE=instance
            return instance
        }
    }
}