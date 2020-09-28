package com.guido.roomforeignkeys.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.guido.roomforeignkeys.entities.Alumno
import com.guido.roomforeignkeys.entities.Cursos
import com.guido.roomforeignkeys.room.dao.AlumnoDAO


@Database(entities = [Alumno::class,Cursos::class],version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    //Dao
    abstract fun alumnoDAO() : AlumnoDAO

    companion object {
        private const val DATABASE_NAME = "rfk-test-db"

        //La anotacion volatile es para que no la cachee
        //Se crea una unica instancia (singleton)
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {})
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}