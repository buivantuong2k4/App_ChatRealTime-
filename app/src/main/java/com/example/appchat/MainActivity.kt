package com.example.appchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appchat.navigation.Home
import com.example.appchat.navigation.Navigition
import com.example.appchat.navigation.Profile
import com.example.appchat.ui.theme.AppChatTheme


class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private lateinit var sharedViewModel: SharedViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        setContent {
            AppChatTheme {
                navHostController = rememberNavController() // Khởi tạo navHostController ở đây
                val isShowBottom by sharedViewModel.isShowBottom.observeAsState(true)
                val isShowtop by sharedViewModel.isShowtop.observeAsState(true)

                Scaffold(
                    topBar = {
                        AnimatedVisibility(visible = isShowtop) {
                            TopAppBar(title ={ Text(text = "Chỉnh sửa trang cá nhân",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )}, modifier = Modifier, navigationIcon ={
                                IconButton(onClick = { navHostController.navigate(Profile) }) {
                                    Image(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                                }
                            } )
                        }
                        },

                    bottomBar = {
                        AnimatedVisibility(visible = isShowBottom) {

                        BottomBar(navHostController) }}
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        Navigition(LocalContext.current, navHostController,sharedViewModel)
                    }
                }
            }
        }
    }

//    override fun onStop() {
//        super.onStop()
//        Utils.Signout(navHostController)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Utils.Signout(navHostController)
//    }
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    val items = listOf("Home", "Profile", "Settings")
    val icons = listOf(Icons.Default.Home, Icons.Default.Person, Icons.Default.Settings)

    // Ghi nhớ route hiện tại
    var selectedRoute by remember { mutableStateOf(items[0]) }

    BottomNavigation(
        backgroundColor = Color.Black,
        contentColor = Color.White,
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                label = {
                    Text(
                        text = item,
                        style = TextStyle(color = Color.White)
                    )
                },
                icon = {
                    Icon(imageVector = icons[index], contentDescription = item)
                },
                selected = selectedRoute == item,
                unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                onClick = {
                    selectedRoute = item
                    when (item) {
                        "Home" -> navHostController.navigate(Home)
                        "Profile" -> navHostController.navigate(Profile)
                        "Settings" -> navHostController.navigate("settings")
                    }
                }
            )
        }
    }
}



@Composable
fun HomeScreen() {
    // Nội dung của màn hình Home
}

@Composable
fun ProfileScreen() {
    // Nội dung của màn hình Profile
}

@Composable
fun SettingsScreen() {
    // Nội dung của màn hình Settings
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppChatTheme {
        Greeting("Android")
    }
}
