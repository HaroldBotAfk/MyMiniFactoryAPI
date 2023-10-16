package com.ru.develop.myminifactory.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ru.develop.myminifactory.data.db.contracts.ModifierObjectContract

@Entity(tableName = ModifierObjectContract.TABLE_NAME)
data class ModifierObject(
    @PrimaryKey
    @ColumnInfo(name = ModifierObjectContract.Columns.ID)
    val id: Int,
    @ColumnInfo(name = ModifierObjectContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = ModifierObjectContract.Columns.AVATAR_URL)
    val avatarUrl: String,
    @ColumnInfo(name = ModifierObjectContract.Columns.COUNT_IMAGES)
    val countImages: Int,
    @ColumnInfo(name = ModifierObjectContract.Columns.IS_SEPARATE)
    var isSeparate: Boolean = false
)
