package com.vbazh.words.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vbazh.words.data.DataConsts
import com.vbazh.words.data.local.entity.LanguageEntity
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface LanguageDao {

    @Query("SELECT * FROM ${DataConsts.DB_LANGUAGE_TABLE}")
    fun getAll(): Single<List<LanguageEntity>>

    @Query("SELECT * FROM ${DataConsts.DB_LANGUAGE_TABLE} WHERE id = :id")
    fun getById(id: String): Observable<LanguageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(languages: List<LanguageEntity>)
}