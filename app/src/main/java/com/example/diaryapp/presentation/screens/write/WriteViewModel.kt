package com.example.diaryapp.presentation.screens.write

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.data.repository.MongoDB
import com.example.diaryapp.model.Diary
import com.example.diaryapp.model.Mood
import com.example.diaryapp.util.Constants
import com.example.diaryapp.model.RequestState
import com.example.diaryapp.util.toRealmInstant
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class WriteViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    init {
        getDiaryIdArgument()
        fetchSelectedDiary()
    }

    private fun getDiaryIdArgument() {
        uiState = uiState.copy(
            selectedDiaryId = savedStateHandle.get<String>(
                key = Constants.WRITE_SCREEN_ARGUMENT_KEY
            )
        )
    }

    private fun fetchSelectedDiary(){
        if(uiState.selectedDiaryId != null) {
            viewModelScope.launch(Dispatchers.Main) {
                MongoDB.getSelectedDiary(
                    //diaryId = ObjectId.invoke(uiState.selectedDiaryId!!)
                    diaryId = uiState.selectedDiaryId!!
                ).collect{ diary ->
                    if(diary is RequestState.Success){
                        setSelectedDiary(diary.data)
                        setTitle(title = diary.data.title)
                        setDescription(description = diary.data.description)
                        setMood(mood = Mood.valueOf(diary.data.mood))
                    }
                }

            }
        }
    }

    private fun setSelectedDiary(diary: Diary) {
        uiState = uiState.copy(selectedDiary = diary)
    }

    fun setTitle(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun setDescription(description: String) {
        uiState = uiState.copy(description = description)
    }

    private fun setMood(mood: Mood) {
        uiState = uiState.copy(mood = mood)
    }


    fun upsertDiary(
        diary: Diary,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            if(uiState.selectedDiaryId != null){
                updateDiary(diary = diary, onSuccess = onSuccess, onError = onError)
            }else{
                insertDiary(diary = diary, onSuccess = onSuccess, onError = onError)
            }
        }
    }

    private fun insertDiary(
        diary: Diary,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val result = MongoDB.addNewDiary(diary = diary.apply {
                if(uiState.updatedDateTime != null){
                    date = uiState.updatedDateTime!!
                }
            })
            if(result is RequestState.Success){
                withContext(Dispatchers.Main){
                    onSuccess()
                }
            } else if (result is RequestState.Error) {
                withContext(Dispatchers.Main){
                    onError(result.error.message.toString())
                }
            }
        }
    }

    fun updateDateTime(zonedDateTime: ZonedDateTime){
        //if(zonedDateTime != null){
            uiState = uiState.copy(updatedDateTime = zonedDateTime.toInstant().toRealmInstant())
//        }else{
//            uiState = uiState.copy(updatedDateTime = null)
//        }
    }

    private suspend fun updateDiary(
        diary: Diary,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        //viewModelScope.launch(Dispatchers.IO) {
            val result = MongoDB.updateDiary(diary = diary.apply {
                //_id = ObjectId.invoke(uiState.selectedDiaryId!!).toString()
                date = if(uiState.updatedDateTime != null){
                    uiState.updatedDateTime!!
                } else {
                    uiState.selectedDiary!!.date
                }
            })
            if(result is RequestState.Success){
                withContext(Dispatchers.Main){
                    onSuccess()
                }
            } else if (result is RequestState.Error) {
                withContext(Dispatchers.Main){
                    onError(result.error.message.toString())
                }
            }
        //}
    }

    fun deleteDiary(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (uiState.selectedDiaryId != null){
                //val result = MongoDB.deleteDiary(id = ObjectId.invoke(uiState.selectedDiaryId!!))
                val result = MongoDB.deleteDiary(id = uiState.selectedDiaryId!!)
                if(result is RequestState.Success){
                    withContext(Dispatchers.Main){
                        onSuccess()
                    }
                }else if (result is RequestState.Error) {
                    withContext(Dispatchers.Main) {
                        onError(result.error.message.toString())
                    }
                }
            }
        }
    }
}

data class UiState(
    val selectedDiaryId: String? = null,
    val selectedDiary: Diary? = null,
    val title: String = "",
    val description: String = "",
    val mood: Mood = Mood.Neutral,
    val updatedDateTime: RealmInstant? = null
)