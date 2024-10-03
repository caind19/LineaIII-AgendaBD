package com.example.ejerciciobd.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ejerciciobd.DAO.UserDao
import com.example.ejerciciobd.Model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    //abstract val Userdatabase: Unit

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        var INSTANCE: UserDatabase? = null


    fun getDatabase(context: Context): UserDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "user_database"
            ).build()
            INSTANCE = instance
            instance
             }
         }
    }
}