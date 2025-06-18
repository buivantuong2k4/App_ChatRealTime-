package com.example.appchat.Screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.appchat.R
import com.example.appchat.Util.StorageUtil
import com.example.appchat.Util.Utils
import com.example.appchat.data.Users
import com.example.appchat.navigation.UpdateProfile
import com.example.appchat.ui.theme.Gray400

@Composable
fun ProfileUserScreen(context: Context,
                      navHostController: NavHostController){
    var User by remember {
        mutableStateOf<Users>(Users())
    }
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }
    LaunchedEffect(true) {
         User= Utils.getCurrentUser()
        uri= StorageUtil.getUriImage(User.imageUrl.toString())
         }
Box(modifier = Modifier
    .fillMaxSize()
    .background(Color.White)){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        SpacerHeight(40.dp)
       ImgUserProfile(uri = uri) {

       }
        SpacerHeight(10.dp)
        Username(name = User.username.toString())
        SpacerHeight(60.dp)


Row (modifier = Modifier
    .fillMaxWidth()
    .padding(horizontal = 15.dp), horizontalArrangement = Arrangement.SpaceBetween){
 ButtonUser(text = "Sửa thông tin ",
     modifier = Modifier
         .width(150.dp)
         .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
     backgroundColor = Color.White) {
     navHostController.currentBackStackEntry?.savedStateHandle?.set("dataUser",User)
     navHostController.currentBackStackEntry?.savedStateHandle?.set("uri",uri)
     navHostController.navigate(UpdateProfile)

 }
    ButtonUser(text = "Xem trang cá nhân  ",
        modifier = Modifier
            .width(190.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
        backgroundColor = Color.White
            ) {

    }
}
    }

}
}


@Composable
fun ImgUserProfile(modifier: Modifier=Modifier,
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
fun Username(
    modifier: Modifier=Modifier,
    name:String
){
Text(text =name, style = TextStyle(
    color = Color.Black,
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold
))
}
@Composable
fun ButtonUser(
    modifier: Modifier=Modifier,
    text: String,
    backgroundColor : Color=Color.White,
    foregroundColor: Color=Color.Black,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(0.dp),
    color: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor,
        contentColor = foregroundColor),
    onClick:()->Unit

){
    Button(onClick = onClick    ,
        modifier = modifier,
        elevation=elevation,
        shape = RoundedCornerShape(10.dp),
        colors = color
    ) {
        Text(text = text,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
                
            )
        )
    }

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Test(){
   // ProfileUserScreen()
}