package com.vbazh.words.data.local

import androidx.room.RoomDatabase
import androidx.room.Database
import com.vbazh.words.data.local.dao.LanguageDao
import com.vbazh.words.data.local.dao.TranslateDao
import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.data.local.entity.TranslateEntity

@Database(
    version = 1, exportSchema = false,
    entities = [
        TranslateEntity::class,
        LanguageEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun translateDao(): TranslateDao

    abstract fun languageDao(): LanguageDao
}