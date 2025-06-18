package com.example.appchat.Screens

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.appchat.R
import com.example.appchat.Viewmodel.SignUpViewmodel
import com.example.appchat.navigation.Login
import com.example.appchat.ui.theme.Gray400
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun SignUpScreen(context :Context,
    navHostController: NavHostController,
                 signUpViewmodel: SignUpViewmodel= viewModel()
){
    val pd= ProgressDialog(context)
    var name by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var gmail by remember {
        mutableStateOf("")
    }
    var passwordrepeat by remember {
        mutableStateOf("")
    }
//    Image Upload
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )
//

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
Column(modifier = Modifier
    .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
HeadSignup(modifier = Modifier,onclick = {navHostController.navigate(Login)})

        ImgUserSignup(modifier = Modifier.padding(top = 30.dp), uri = uri, onclick = {
        singlePhotoPicker.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
            Log.e("loi","uri : $uri")
    })

  TextfielSignup(modifier = Modifier.padding(top = 30.dp),
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
    ButtonSignup (modifier = Modifier
    ) {
if (name.isNotEmpty() && gmail.isNotEmpty() && passwordrepeat.isNotEmpty() && password.isNotEmpty()){
    signUpViewmodel.createAnAccount(uri,name, password, gmail,context)
    name=""
    password=""
    passwordrepeat=""
    gmail=""
    uri=null

}
        else
            Toast.makeText(context,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show()

    }
}
    }
}

@Composable
fun HeadSignup(modifier: Modifier=Modifier,onclick: () -> Unit){
    Row (modifier = modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween){
        Text(text = stringResource(R.string.login),
            style = TextStyle(
                color = Color.Gray,
                fontSize = 20.sp,
            ),
                    modifier=Modifier.clickable { onclick() }
        )
//
        Text(text = stringResource(R.string.sign_up),
            style = TextStyle(
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold),



        )

    }
}
fun Createaccont(email :String,password: String){
    val auth : FirebaseAuth = FirebaseAuth.getInstance()
 val   firestore = FirebaseFirestore.getInstance()
    val dataHashMap = hashMapOf("userid" to 12345, "username" to password, "useremail" to email, "status" to "default",
        "imageUrl" to "https://www.pngarts.com/files/6/User-Avatar-in-Suit-PNG.png")


    firestore.collection("Users").document("12345").set(dataHashMap)
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task->

        if (task.isSuccessful){
            Log.e("loi","4")}}



}
@Composable
fun ImgUserSignup(modifier: Modifier=Modifier,
                  uri: Uri?,
onclick: () -> Unit){
   if(uri!=null){
        AsyncImage(model = uri,
            contentDescription = null,
            modifier = modifier
                .size(170.dp)
                .clip(CircleShape)
                .border(5.dp, Gray400, CircleShape)

                .clickable { onclick() })
}
else {
    IconComponentDrawable(icon = R.drawable.user, size =170.dp, modifier =modifier
        .clip(CircleShape)
        .border(5.dp, Gray400, CircleShape)
        .clickable { onclick() })
   }
}
@Composable
fun TextfielSignup(
    modifier: Modifier=Modifier,
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

        OutlinedTextField(
            value = gmail,
            onValueChange = Onchangegmail,
            label = { Text(text = stringResource(R.string.email_address)) },
            placeholder = { Text(text = stringResource(R.string.email_address)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color .Black,
                focusedTextColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                errorPlaceholderColor = Color.Transparent
            ),
        )
            SpacerHeight()
            OutlinedTextField(value = username,
                onValueChange =Onchangeusername,
                label = { Text(text = stringResource(R.string.username_or_email_adress))},
                placeholder ={ Text(text = stringResource(R.string.username_or_email_adress))},
                singleLine = true,
                colors =OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color .Black,
                    focusedTextColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    errorPlaceholderColor = Color.Transparent
                ),
            )
        SpacerHeight()
        OutlinedTextField(
            value = passwordrepeat,
            onValueChange = Onchangerepeatpassword,
            label = { Text(text = stringResource(R.string.repeat_password)) },
            placeholder = { Text(text = stringResource(R.string.repeat_password)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color .Black,
                focusedTextColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                errorPlaceholderColor = Color.Transparent
            ),
            trailingIcon = { IconComponentImageVector(icon = Icons.Default.Lock, size = 20.dp, tint = Color.Black) }
        )
            SpacerHeight()
            OutlinedTextField(
                value = password,
                onValueChange = Onchangepassword,
                label = { Text(text = stringResource(R.string.password)) },
                placeholder = { Text(text = stringResource(R.string.password)) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedTextColor = Color .Black,
                    focusedTextColor = Color.Black,
                    disabledContainerColor = Color.Transparent,
                    errorPlaceholderColor = Color.Transparent
                ),
                trailingIcon = { IconComponentImageVector(icon = Icons.Default.Lock, size = 20.dp, tint = Color.Black) }
            )
    }}
}
@Composable
fun ButtonSignup(
    modifier: Modifier=Modifier,
    onclick:()->Unit
){

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(horizontal = 50.dp)
        .border(3.dp, Gray400, RoundedCornerShape(30.dp)) ,

        contentAlignment = Alignment.Center){
    ButtonComponent (modifier = Modifier.fillMaxSize(), text = "Sign Up"){onclick()
    }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Test3(){
//SignUpScreen()
}