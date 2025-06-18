package com.example.appchat.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appchat.Screens.ChatScreen
import com.example.appchat.Screens.HomeScreen
import com.example.appchat.Screens.LoginScreen
import com.example.appchat.Screens.ProfileUserScreen
import com.example.appchat.Screens.SignUpScreen
import com.example.appchat.Screens.StartScreen
import com.example.appchat.Screens.UpdateProfileScreen
import com.example.appchat.SharedViewModel

@Composable
fun Navigition(context: Context,navHostController: NavHostController,sharedViewModel: SharedViewModel){



    NavHost(navController = navHostController, startDestination = Start) {
        composable(Start) {
            sharedViewModel.setisShowBottom(false)
            StartScreen(

                navHostController
            )
        }
        composable(Home) {
            sharedViewModel.setisShowtop(false)
            sharedViewModel.setisShowBottom(true)
            HomeScreen(
                navHostController
            )
        }
        composable(Chat) {
            sharedViewModel.setisShowtop(false)
            sharedViewModel.setisShowBottom(false)
            ChatScreen(navHostController)
        }
        composable(Login) {
            sharedViewModel.setisShowtop(false)
            sharedViewModel.setisShowBottom(false)
            LoginScreen(context, navHostController)
        }
        composable(SignUp) {
            sharedViewModel.setisShowtop(false)
            sharedViewModel.setisShowBottom(false)
            SignUpScreen(context, navHostController)
        }
        composable(Profile) {
            sharedViewModel.setisShowtop(false)
            sharedViewModel.setisShowBottom(true)
            ProfileUserScreen(context, navHostController)
        }
        composable(UpdateProfile) {
            sharedViewModel.setisShowtop(true)
            sharedViewModel.setisShowBottom(true)
          UpdateProfileScreen(context, navHostController)
        }
    }

}

const val  Start="start_screen"
const val Home ="home_screen"
const val Chat="chat_screen"
const val Login="login_screen"
const val SignUp="signup_screen"
const val Profile="profile_screen"
const val UpdateProfile="updateprofile_screen"