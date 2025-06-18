@file:Suppress("UNUSED_EXPRESSION")

package com.example.appchat.Screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.appchat.R
import com.example.appchat.Util.StorageUtil
import com.example.appchat.Util.Utils
import com.example.appchat.Viewmodel.HomeViewmodel
import com.example.appchat.data.RecentChats
import com.example.appchat.data.Users
import com.example.appchat.ui.theme.Gray400
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(context: Context,
                        navHostController: NavHostController,
                        homeViewmodel: HomeViewmodel= viewModel()
){
    val  User=navHostController.previousBackStackEntry?.savedStateHandle?.get<Users>("dataUser")?: Users()
    val  uriImage =navHostController.previousBackStackEntry?.savedStateHandle?.get<Uri>("uri")
val chatList by homeViewmodel.chatList.observeAsState(emptyList())
    var uri by remember {
        mutableStateOf<Uri?>(uriImage)
    }
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )
//



   // val pd= ProgressDialog(context)
    var name by remember {
        mutableStateOf(User.username.toString())
    }
    var password by remember {
        mutableStateOf("")
    }
    var gmail by remember {
        mutableStateOf(User.useremail.toString())
    }
    var passwordrepeat by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        ) {
        Column(modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImgUserUpdate(modifier = Modifier.padding(top = 20.dp),uri=uri, onclick = {
                singlePhotoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            })
            TextfielUpdate(modifier = Modifier.padding(top = 30.dp),
                gmail = gmail,
                username = name,
                password = password,
                passwordrepeat = passwordrepeat,
                Onchangeusername = {name=it},
                Onchangepassword = {password=it},
                Onchangegmail = {gmail=it}
            ) {
                passwordrepeat=it
            }
            SpacerHeight(40.dp)
            ButtonUpdate (modifier = Modifier
            ) {
                if (name.isNotEmpty() && gmail.isNotEmpty() ){
                  if (uri==uriImage){
                      updateProfile(username = name, imageUrl =User.imageUrl.toString(),chatList,context )
                  }
                    else{
                    val unique_image_name = UUID.randomUUID()
                    StorageUtil.uploadToStorage(uri, context, "image", unique_image_name.toString())
                    updateProfile(username = name, imageUrl = unique_image_name.toString(),chatList,context)
                    }


                }
              //  else
                //    Toast.makeText(context,"Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()

            }
        }
    }
}



@Composable
fun ImgUserUpdate(modifier: Modifier=Modifier,
                  uri: Uri?,
                  onclick: () -> Unit){
    if(uri!=null){
        AsyncImage(model = uri,
            contentDescription = null,
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(5.dp, Gray400, CircleShape)

                .clickable { onclick() })
    }
    else {
        IconComponentDrawable(icon = R.drawable.user, size =100.dp, modifier = modifier
            .clip(CircleShape)
            .border(5.dp, Gray400, CircleShape)
            .clickable { onclick() })
    }
}
@Composable
fun TextfielUpdate(
    modifier: Modifier = Modifier,
    gmail:String,
    username: String,
    password :String,
    passwordrepeat :String,
    Onchangeusername :(String)->Unit,
    Onchangepassword :(String)->Unit,
    Onchangegmail :(String)->Unit,
    Onchangerepeatpassword :(String)->Unit
){
    Box(modifier = modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center){
        Column {

            TextField(value = gmail,
                onValueChange = Onchangegmail,
                label = { Text(text = stringResource(R.string.email_address)) },
                singleLine = true,
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color .Black,
                    focusedTextColor = Color.Black,
                    disabledContainerColor = Color.White,
                    errorPlaceholderColor = Color.Transparent,
                   unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                    ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),


                )

            SpacerHeight()
            TextField(value = username,
                onValueChange = Onchangeusername,
                label = { Text(text = "Username") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color .Black,
                    focusedTextColor = Color.Black,
                    disabledContainerColor = Color.White,
                    errorPlaceholderColor = Color.Transparent,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),


                )
            SpacerHeight()
            TextField(value = password,
                onValueChange = Onchangepassword,
                label = { Text(text = "password mới ") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color .Black,
                    focusedTextColor = Color.Black,
                    disabledContainerColor = Color.White,
                    errorPlaceholderColor = Color.Transparent,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),

                trailingIcon = { IconComponentImageVector(icon = Icons.Default.Lock, size = 20.dp, tint = Color.Black) }
                )
            SpacerHeight()
            TextField(value = passwordrepeat,
                onValueChange = Onchangerepeatpassword,
                label = { Text(text = "Nhập  Password ") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color .Black,
                    focusedTextColor = Color.Black,
                    disabledContainerColor = Color.White,
                    errorPlaceholderColor = Color.Transparent,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),

                trailingIcon = { IconComponentImageVector(icon = Icons.Default.Lock, size = 20.dp, tint = Color.Black) }
            )
        }
    }
}
@Composable
fun ButtonUpdate(
    modifier: Modifier = Modifier,
    onclick:  ()->Unit
){
       Button(onClick = {
           onclick()
       },
           modifier = modifier.background(Color.White),
           shape = RoundedCornerShape(10.dp)
       ) {
           Text(text = "Lưu")
       }
        }


fun updateProfile(username :String, imageUrl :String, chatList:List<RecentChats>,context: Context) {
    val firestore = FirebaseFirestore.getInstance()
   // val context = MyApplication.instance.applicationContext

    val hashMapUser =
        hashMapOf<String, Any>("username" to username, "imageUrl" to imageUrl)

    firestore.collection("Users").document(Utils.getUidLoggedIn()).update(hashMapUser).addOnCompleteListener {

        if (it.isSuccessful){

            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT ).show()

        }

    }




    for (item in chatList){
        if (item.person=="you"){
            val hashMapUpdate = hashMapOf<String, Any>("friendsimage" to imageUrl, "name" to username, "person" to username)


            firestore.collection("Conversation${item.friendid}").document(Utils.getUidLoggedIn()).update(hashMapUpdate)
        }
        else{
            val hashMapUpdate = hashMapOf<String, Any>("friendsimage" to imageUrl, "name" to username)

            // updating the chatlist and recent list message, image etc

            firestore.collection("Conversation${item.friendid}").document(Utils.getUidLoggedIn()).update(hashMapUpdate)


        }

    }




}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Test4(){
//UpdateProfileScreen()
}