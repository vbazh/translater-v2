package com.vbazh.words.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vbazh.words.data.DataConsts

@Entity(tableName = DataConsts.DB_LANGUAGE_TABLE)
data class LanguageEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = DataConsts.DB_LANGUAGE_NAME_SOURCE_FIELD)
    val name: String
)