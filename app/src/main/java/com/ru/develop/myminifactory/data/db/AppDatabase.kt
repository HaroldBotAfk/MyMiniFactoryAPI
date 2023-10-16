package com.ru.develop.myminifactory.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ru.develop.myminifactory.data.db.dao.ModifierObjectDao
import com.ru.develop.myminifactory.data.models.ModifierObject

@Database(
    entities = [
        ModifierObject::class
    ], version = AppDatabase.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun modifierObjectDao(): ModifierObjectDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "app_database"
    }
}