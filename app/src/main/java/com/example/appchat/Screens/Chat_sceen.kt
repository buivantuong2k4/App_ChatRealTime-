package com.example.appchat.Screens

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.appchat.R
import com.example.appchat.Util.StorageUtil
import com.example.appchat.Util.Utils
import com.example.appchat.Viewmodel.ChatAppViewModel
import com.example.appchat.Viewmodel.MessageViewmodel
import com.example.appchat.data.Messages
import com.example.appchat.data.Users
import com.example.appchat.ui.theme.Gray
import com.example.appchat.ui.theme.Gray400
import com.example.appchat.ui.theme.LightRed
import com.example.appchat.ui.theme.LightYellow
import com.example.appchat.ui.theme.Yellow

@Composable
fun ChatScreen(
    navHostController: NavHostController,
    chatAppViewModel: ChatAppViewModel= viewModel(),
    messageViewmodel: MessageViewmodel= viewModel()
){

 val  receiver=navHostController.previousBackStackEntry?.savedStateHandle?.get<Users>("data")?:Users()


    val messagesList by messageViewmodel.getmessages(receiver.userid.toString()).observeAsState(emptyList())
    var message by remember {
        mutableStateOf("")
    }

    Box (modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
       ){
        
        Column(modifier = Modifier.fillMaxSize()) {
            UserEachRow(user = receiver, modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp))
            Box (modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White, RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    )
                )
                .padding(top = 25.dp)){
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp, top = 25.dp, bottom = 75.dp)) {
                    items(messagesList){
                      ChatRow(modifier = Modifier, messages =it )
                    }
                }

                TextFieldComponent(text = message, onValueChange = {message=it}, modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .align(BottomCenter) ) {
                    chatAppViewModel.sendMessage(receiver,message)
                    message=""
                }
                
            }
        }
    }
}
@Composable
fun UserEachRow(
    modifier: Modifier=Modifier,
   user: Users
    
){
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }
    LaunchedEffect(true) {
        uri= StorageUtil.getUriImage(user.imageUrl.toString())
    }
    Row (modifier = modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween){
      
        Row {
            if (uri !=null)
                AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(60.dp))
            else
                AsyncImage(model = user.imageUrl, contentDescription = null, modifier = Modifier.size(60.dp))
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = user.username.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(text = stringResource(R.string.online),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                       
                    )
                )
            }
        }
        IconComponentImageVector(icon = Icons.Default.MoreVert, size =24.dp, tint = Color.White )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    modifier: Modifier=Modifier,
    text:String,
    onValueChange:(String)->Unit,
    send:()->Unit
){
    TextField(value = text, onValueChange =onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(160.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Gray400,
            unfocusedTextColor = Color.Transparent,
            focusedTextColor = Color.Black
        ),
        placeholder = {
            Text(text = stringResource(R.string.type_message),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
        },
        leadingIcon = { IconButtonComponentImageVecto(icon = Icons.Default.Add){} },
        trailingIcon = { IconButtonComponentImageVecto(icon = Icons.Default.Send){send()} }

    )
}
@Composable
fun IconButtonComponentDrawable(
    @DrawableRes icon :Int
){
    Box (modifier = Modifier
        .background(Yellow, CircleShape)
        .size(33.dp),
        contentAlignment = Alignment.Center){
        IconComponentDrawable(icon = icon, size =15.dp , tint = Color.Black)
    }
}
@Composable
fun IconButtonComponentImageVecto(
     icon :ImageVector,
     onclick:()->Unit

){
    Box (modifier = Modifier
        .background(Yellow, CircleShape)
        .size(33.dp)
        .clickable { onclick() },
        contentAlignment = Alignment.Center){
     IconComponentImageVector(icon = icon, size =15.dp, tint = Color.Black )
    }
}
@Composable
fun ChatRow(modifier: Modifier,
 messages: Messages
){
    Column(modifier = modifier
        .fillMaxWidth(),
        horizontalAlignment = if(messages.sender== Utils.getUidLoggedIn())Alignment.End else Alignment.Start
        ) {
        Box(modifier = Modifier.background(
            if(messages.sender== Utils.getUidLoggedIn()) LightRed else LightYellow, RoundedCornerShape(100.dp)
        ),
            contentAlignment = Alignment.Center){
            Text(text = messages.message.toString(),
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black
                ),
                textAlign = TextAlign.End,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp)
            )
        }
        Text(text = messages.time.toString(),
            style = TextStyle(
                fontSize = 12.sp,
                color = Gray
            ),
            textAlign = TextAlign.End,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp)
        )
    }
}