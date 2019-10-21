package com.vbazh.words.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vbazh.words.data.DataConsts
import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

@Dao
interface TranslateDao {

    @Query("SELECT * FROM ${DataConsts.DB_TRANSLATE_TABLE}")
    fun getAll(): Flowable<List<TranslateEntity>>

//    @Query("SELECT * FROM DataConsts.DB_TRANSLATE_TABLE WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>

//    @Query("SELECT * FROM ${DataConsts.DB_TRANSLATE_TABLE} WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): TranslateEntity

    @Insert
    fun insertAll(vararg translates: TranslateEntity)

    @Delete
    fun delete(user: TranslateEntity): Completable
}