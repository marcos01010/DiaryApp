package com.example.diaryapp.presentation.screens.write

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.diaryapp.model.Diary
import com.example.diaryapp.model.Mood
import java.time.ZonedDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WriteScreen(
    uiState: UiState,
    moodName: () -> String,
    pagerState: PagerState,
    onTitleChange: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDeleteConfirmed: () -> Unit,
    onDateTimeUpdated: (ZonedDateTime) -> Unit,
    onBackPressed: () -> Unit,
    onSaveClicked: (Diary) -> Unit
) {

    LaunchedEffect(key1 = uiState.mood) {
        pagerState.scrollToPage(Mood.valueOf(uiState.mood.name).ordinal)
    }

    Scaffold(
        topBar = {
            WriteTopBar(
                selectedDiary = uiState.selectedDiary,
                moodName = moodName,
                onDeleteConfirmed = onDeleteConfirmed,
                onBackPressed = onBackPressed,
                onDateTimeUpdated = onDateTimeUpdated
            )
        },
        content = {
            WriteContent(
                uiState = uiState,
                pagerState = pagerState,
                title = uiState.title,
                onTitleChanged = onTitleChange,
                description = uiState.description,
                onDescriptionChanged = onDescriptionChanged,
                paddingValues = it,
                onSaveClicked = onSaveClicked
            )
        }
    )
}