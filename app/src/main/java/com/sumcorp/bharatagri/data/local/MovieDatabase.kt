package com.sumcorp.bharatagri.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sumcorp.bharatagri.data.local.dao.MovieDao
import com.sumcorp.bharatagri.data.local.entity.model.MovieListResponse
import com.sumcorp.bharatagri.data.local.typeConverter.MovieTypeConverter

@Database(entities = [MovieListResponse::class], version = 1)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDatabase : RoomDatabase(){

    companion object {
        private const val DATABASE_NAME = "movie-app"

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                var instace = INSTANCE

                if (instace == null) {
                    instace = Room.databaseBuilder(
                        context.applicationContext, MovieDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }

                INSTANCE = instace
                return instace

            }

        }
    }

    abstract fun movieDao(): MovieDao
}