package com.example.diaryapp.connectivity

import com.example.util.connectivity.ConnectivityObserver
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<ConnectivityObserver.Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}