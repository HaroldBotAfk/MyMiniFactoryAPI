package com.ru.develop.myminifactory.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ru.develop.myminifactory.data.models.ModifierObject

@Dao
interface ModifierObjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModifierObject(modifierObject: List<ModifierObject>)
}