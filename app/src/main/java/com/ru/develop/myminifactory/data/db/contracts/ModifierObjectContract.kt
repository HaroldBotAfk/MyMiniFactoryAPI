package com.ru.develop.myminifactory.data.db.contracts

object ModifierObjectContract {
    const val TABLE_NAME = "modifier_objects"

    object Columns {
        const val ID = "modifier_objects_id"
        const val NAME = "modifier_objects_name"
        const val AVATAR_URL = "modifier_object_avatar_url"
        const val COUNT_IMAGES = "modifier_objects_count_images"
        const val IS_SEPARATE = "modifier_objects_is_separate"
    }
}