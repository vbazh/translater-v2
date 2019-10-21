package com.vbazh.words.data.local.entity

import androidx.room.Entity
import com.vbazh.words.data.DataConsts
import androidx.room.ColumnInfo
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4
@Entity(tableName = DataConsts.DB_TRANSLATE_TABLE)
data class TranslateEntity(

    @ColumnInfo(name = DataConsts.DB_ROW_ID)
    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null,

    @ColumnInfo(name = DataConsts.DB_TRANSLATE_SOURCE_FIELD)
    val source: String,

    @ColumnInfo(name = DataConsts.DB_TRANSLATE_TARGET_FIELD)
    var target: String,

    @ColumnInfo(name = DataConsts.DB_TRANSLATE_DIRECTION_FIELD)
    var direction: String
)