package com.example.diaryapp.presentation.screens.write

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapp.data.repository.MongoDB
import com.example.diaryapp.model.Diary
import com.example.diaryapp.model.GalleryImage
import com.example.diaryapp.model.GalleryState
import com.example.diaryapp.model.Mood
import com.example.diaryapp.util.Constants
import com.example.diaryapp.model.RequestState
import com.example.diaryapp.model.rememberGalleryState
import com.example.diaryapp.util.fetchImagesFromFirebase
import com.example.diaryapp.util.toRealmInstant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class WriteViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val galleryState = GalleryState()
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

                        diary.data.images.forEach {
                            galleryState.addImage(
                                GalleryImage(
                                    image = it.toUri()
                                )
                            )
                        }

                        /*fetchImagesFromFirebase(
                            remoteImagePaths = diary.data.images,
                            onImageDownload = { downloadedImage ->
                                galleryState.addImage(
                                    GalleryImage(
                                        image = downloadedImage,
                                        remoteImagePath = extractImagePath(
                                            fullImageUrl = downloadedImage.toString()
                                        )
                                    )
                                )
                            }
                        )*/
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
                uploadImagesToFirebase()
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
                uploadImagesToFirebase()
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

    fun addImage(image: Uri, imageType: String){
//        val remoteImagePath = "images/${FirebaseAuth.getInstance().currentUser?.uid}/" +
//                "${image.lastPathSegment}-${System.currentTimeMillis()}.$imageType"
        val remoteImagePath = "images/1/" +
                "${image.lastPathSegment}-${System.currentTimeMillis()}.$imageType"
        Log.d("WriteViewModel", remoteImagePath)
        galleryState.addImage(
            GalleryImage(
                image = image,
                remoteImagePath = remoteImagePath
            )
        )
    }

    private fun uploadImagesToFirebase(){
        val storage = FirebaseStorage.getInstance().reference
        galleryState.images.forEach {galleryImage ->
            val imagePth = storage.child(galleryImage.remoteImagePath)
            imagePth.putFile(galleryImage.image)
        }
    }

    private fun extractImagePath(fullImageUrl: String): String {
        val chunks = fullImageUrl.split("%2F")
        val imageName = chunks[2].split("?").first()
        return "images/${Firebase.auth.currentUser?.uid}/$imageName"
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