package com.example.diaryapp.data.repository

import com.example.diaryapp.model.Diary
import com.example.diaryapp.model.RequestState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

typealias Diaries = RequestState<Map<LocalDate, List<Diary>>>

interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries(): Flow<Diaries>
    //fun getSelectedDiary(diaryId: ObjectId): RequestState<Diary>
    fun getSelectedDiary(diaryId: String): Flow<RequestState<Diary>>
    fun addNewDiary(diary: Diary): RequestState<Diary>
    suspend fun updateDiary(diary: Diary): RequestState<Diary>
    //suspend fun deleteDiary(id: ObjectId): RequestState<Diary>
    suspend fun deleteDiary(id: String): RequestState<Boolean>
}