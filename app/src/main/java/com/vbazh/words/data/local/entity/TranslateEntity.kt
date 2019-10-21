package com.vbazh.words.data.local.entity

import androidx.room.Entity
import com.vbazh.words.data.DataConsts
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = DataConsts.DB_TRANSLATE_TABLE)
data class TranslateEntity(

    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null,

    @ColumnInfo(name = DataConsts.DB_TRANSLATE_SOURCE_FIELD)
    val source: String,

    @ColumnInfo(name = DataConsts.DB_TRANSLATE_TARGET_FIELD)
    var target: String,

    @ColumnInfo(name = DataConsts.DB_TRANSLATE_DIRECTION_FIELD)
    var direction: String
)