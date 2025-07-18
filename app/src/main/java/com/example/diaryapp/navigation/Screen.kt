package com.example.diaryapp.navigation

import com.example.diaryapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY

sealed class Screen(val route: String){
    object Authentication: Screen(route = "authentication_screen")
    object Home: Screen(route = "some_screen")
    object Write: Screen(route = "write_screen?${WRITE_SCREEN_ARGUMENT_KEY}={${WRITE_SCREEN_ARGUMENT_KEY}}"){
        fun passDiaryId(diaryId: String) =
            "write_screen?${WRITE_SCREEN_ARGUMENT_KEY}=${diaryId}"
    }
}