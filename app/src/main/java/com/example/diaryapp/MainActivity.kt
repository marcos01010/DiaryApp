package com.example.diaryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.diaryapp.navigation.Screen
import com.example.diaryapp.navigation.SetupNavGraph
import com.example.diaryapp.ui.theme.DiaryAppTheme
import com.example.diaryapp.util.Constants.APP_ID
import com.google.firebase.FirebaseApp
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {

    var keepSplashOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        FirebaseApp.initializeApp(this)
        setContent {
            DiaryAppTheme(dynamicColor = false) {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = getStartDestination(),
                    navController = navController,
                    onDateLoaded = {
                        keepSplashOpened = false
                    }
                )
            }
        }
    }

    private fun getStartDestination(): String {
        val user = App.create(APP_ID).currentUser
        return if (user != null && user.loggedIn) {
            Screen.Home.route
        } else {
            Screen.Authentication.route
        }
    }
}