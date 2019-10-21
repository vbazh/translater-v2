package com.vbazh.words.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vbazh.words.data.DataConsts
import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TranslateDao {

    @Query("SELECT *, ${DataConsts.DB_ROW_ID} FROM ${DataConsts.DB_TRANSLATE_TABLE}")
    fun getAll(): Flowable<List<TranslateEntity>>

    @Query("SELECT * FROM ${DataConsts.DB_TRANSLATE_TABLE} WHERE ${DataConsts.DB_TRANSLATE_SOURCE_FIELD} LIKE '%' || :search || '%' OR ${DataConsts.DB_TRANSLATE_TARGET_FIELD} LIKE '%' || :search || '%'")
    fun findByText(search: String): Single<List<TranslateEntity>>

    @Insert
    fun insertAll(vararg translates: TranslateEntity)

    @Delete
    fun delete(user: TranslateEntity): Completable

    @Query("DELETE FROM ${DataConsts.DB_TRANSLATE_TABLE} WHERE ${DataConsts.DB_ROW_ID} = :id")
    fun deleteById(id: Int): Completable
}