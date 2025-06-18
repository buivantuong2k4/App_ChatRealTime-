package com.example.appchat.Screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.appchat.R
import com.example.appchat.Util.StorageUtil
import com.example.appchat.Util.Utils
import com.example.appchat.Viewmodel.ChatAppViewModel
import com.example.appchat.Viewmodel.HomeViewmodel
import com.example.appchat.data.RecentChats
import com.example.appchat.data.Users
import com.example.appchat.navigation.Chat
import com.example.appchat.ui.theme.DarkGray
import com.example.appchat.ui.theme.Gray
import com.example.appchat.ui.theme.Gray400
import com.example.appchat.ui.theme.Line
import com.example.appchat.ui.theme.Yellow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
 navHostController: NavHostController,
 homeViewmodel: HomeViewmodel= viewModel()
){
val chatList by homeViewmodel.chatList.observeAsState(emptyList())

//    val userList by homeViewmodel.users.observeAsState(emptyList())
Box(modifier = Modifier
    .fillMaxSize()
    .background(Color.Black)){
    Column(modifier = Modifier
        .fillMaxSize()
       ) {

HeaderAndStory(navHostController = navHostController) {
    Utils.Signout(navHostController)
}
        Box (modifier = Modifier
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            ){

BottomSheetSwipe(modifier = Modifier
    .align(Alignment.TopCenter)
    .padding(top = 15.dp))

            LazyColumn (modifier = Modifier.padding(top = 30.dp, bottom = 15.dp)){
items(chatList){

    UserEachRow(recentChats = it) {
        GlobalScope.launch(Dispatchers.Main){
        Log.e("loi","receiveid ${it.friendid}")
        val receiver = Utils.getFriendUser(it.friendid.toString())

        Log.e("loi","receive $receiver")
       navHostController.currentBackStackEntry?.savedStateHandle?.set("data",receiver)
       navHostController.navigate(Chat)
    }}
}
            }
        }
    }
}
}

@Composable
fun Header(chatAppViewModel: ChatAppViewModel= viewModel()){
var User by remember {
   mutableStateOf("")
}
    LaunchedEffect(true) {
        val users= Utils.getCurrentUser()
     User=users.username!! }
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.W300
            )
        ){
            append("wellcome ")
        }

        withStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        ){
            append(User)
        }
    }
    Text(text = text)
}

@Composable
fun AddStoryLayout(){
Column {
    Box(modifier = Modifier
        .border(
            2.dp, DarkGray, shape = CircleShape
        )
        .background(Yellow, shape = CircleShape)
        .size(70.dp),
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(Color.Black)
            .size(20.dp),
            contentAlignment = Alignment.Center){
            IconComponentImageVector(icon = Icons.Default.Add, size =12.dp, tint = Yellow )
        }
    }
    SpacerHeight(8.dp)
    Text(text = stringResource(R.string.add_story),
        style = TextStyle(
            color = Color.White,
            fontSize = 13.sp,
        ),
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )
}
}

@Composable
fun UserStoryLayout(
    modifier: Modifier=Modifier,
   users: Users,
   onclick: () -> Unit
){
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }
    LaunchedEffect(true) {
         uri= StorageUtil.getUriImage(users.imageUrl.toString())
    }
Column(modifier = Modifier.padding(end = 10.dp)) {
Box (modifier = Modifier
    .clip(CircleShape)
    .background(Yellow, CircleShape)
    .clickable { onclick() },
    contentAlignment = Alignment.Center){
//IconComponentDrawable(icon = person.icon, size =65.dp )
if (uri !=null)
    AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(70.dp))
    else
    AsyncImage(model = users.imageUrl, contentDescription = null, modifier = Modifier.size(70.dp))
}

    SpacerHeight(8.dp)
    Text(text = users.username.toString(),
        style = TextStyle(
            color = Color.White,
            fontSize = 13.sp,
        ),
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )
}
}
//
//List Users
@Composable
fun ViewStoryLayout(viewmodel: HomeViewmodel= viewModel(),navHostController: NavHostController){
    Log.e("loi","1")
    val userList by viewmodel.users.observeAsState(emptyList())
    Log.e("loi","2 : $userList")
LazyRow(modifier = Modifier.padding(vertical = 20.dp)) {
item {
    AddStoryLayout()
    SpacerWith()
}
    items(userList){user->
        Log.e("loi","3 :$user")
    UserStoryLayout(users = user) {
        navHostController.currentBackStackEntry?.savedStateHandle?.set("data",user)
        navHostController.navigate(Chat)
    }
    }

}
}

@Composable
fun HeaderAndStory(navHostController: NavHostController,
                   onclick: () -> Unit){
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, top = 40.dp)){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
Header()
            Image(imageVector = Icons.Default.ExitToApp,
                contentDescription =null,
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.White)
                    .clickable { onclick() }
            )
        }
        ViewStoryLayout(navHostController = navHostController)
    }
}

@Composable
fun BottomSheetSwipe(modifier: Modifier=Modifier){
    Box(modifier = modifier
        .background(Gray400, RoundedCornerShape(90.dp))
        .width(90.dp)
        .height(5.dp)
    )
}

@Composable
fun UserEachRow(
    modifier: Modifier=Modifier,
 recentChats: RecentChats,
    onclick: ()->Unit
){
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }
    LaunchedEffect(true) {
        uri= StorageUtil.getUriImage(recentChats.friendsimage.toString())
    }
    Box (modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .clickable { onclick() }
        .padding(horizontal = 20.dp, vertical = 5.dp)){
        Column {
            Row(modifier=Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween  ) {
                Row {
                    if (uri !=null)
                        AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(50.dp))
                    else
                        AsyncImage(model = recentChats.friendsimage, contentDescription = null, modifier = Modifier.size(50.dp))
                    SpacerWith()
                    Column {


                    Text(text = recentChats.name.toString(),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    SpacerHeight(5.dp)
                    Text(text = "${recentChats.person}  :  ${recentChats.message}",
                        style = TextStyle(
                            color = Gray,
                            fontSize = 14.sp

                        )
                    )}
                }
                Text(text = recentChats.time.toString(),
                    style = TextStyle(
                        color = Gray,
                        fontSize = 12.sp))

            }
            SpacerHeight(15.dp)
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Line)
        }

    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun test1(){

  // HomeScreen()

}
