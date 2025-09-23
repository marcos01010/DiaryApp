package com.example.diaryapp.data.repository

import com.example.util.model.Diary
import com.example.util.model.RequestState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZonedDateTime

typealias Diaries = RequestState<Map<LocalDate, List<Diary>>>

interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries(): Flow<Diaries>
    //fun getSelectedDiary(diaryId: ObjectId): RequestState<Diary>
    fun getFilteredDiaries(zonedDateTime: ZonedDateTime): Flow<Diaries>
    fun getSelectedDiary(diaryId: String): Flow<RequestState<Diary>>
    fun addNewDiary(diary: Diary): RequestState<Diary>
    suspend fun updateDiary(diary: Diary): RequestState<Diary>
    //suspend fun deleteDiary(id: ObjectId): RequestState<Diary>
    suspend fun deleteDiary(id: String): RequestState<Boolean>
    suspend fun deleteAllDiaries(): RequestState<Boolean>
}