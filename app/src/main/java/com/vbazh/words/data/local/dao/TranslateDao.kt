package com.vbazh.words.data.local.dao

import androidx.room.*
import com.vbazh.words.data.DataConsts
import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TranslateDao {

    @Query("SELECT *, ${DataConsts.DB_ROW_ID} FROM ${DataConsts.DB_TRANSLATE_TABLE}")
    fun getAll(): Flowable<List<TranslateEntity>>

    @Query("SELECT *, ${DataConsts.DB_ROW_ID} FROM ${DataConsts.DB_TRANSLATE_TABLE} WHERE ${DataConsts.DB_TRANSLATE_TABLE} = 1")
    fun getFavorites(): Flowable<List<TranslateEntity>>

    @Query("SELECT * FROM ${DataConsts.DB_TRANSLATE_TABLE} WHERE ${DataConsts.DB_TRANSLATE_SOURCE_FIELD} LIKE '%' || :search || '%' OR ${DataConsts.DB_TRANSLATE_TARGET_FIELD} LIKE '%' || :search || '%'")
    fun findByText(search: String): Single<List<TranslateEntity>>

    @Query("SELECT * FROM ${DataConsts.DB_TRANSLATE_TABLE} WHERE ${DataConsts.DB_TRANSLATE_SOURCE_FIELD} LIKE '%' || :search || '%' OR ${DataConsts.DB_TRANSLATE_TARGET_FIELD} LIKE '%' || :search || '%' AND ${DataConsts.DB_TRANSLATE_FAVORITE_FIELD} = 1")
    fun findFavoriteByText(search: String): Single<List<TranslateEntity>>

    @Insert
    fun insertAll(vararg translates: TranslateEntity)

    @Delete
    fun delete(entity: TranslateEntity): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: TranslateEntity): Completable

    @Query("DELETE FROM ${DataConsts.DB_TRANSLATE_TABLE} WHERE ${DataConsts.DB_ROW_ID} = :id")
    fun deleteById(id: Int): Completable
}