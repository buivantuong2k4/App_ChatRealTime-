package com.example.appchat.Screens

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
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
import com.example.appchat.R
import com.example.appchat.Viewmodel.SignInViewmodel
import com.example.appchat.navigation.SignUp
import com.example.appchat.ui.theme.Gray400

@Composable
fun LoginScreen(context: Context,
    navHostController: NavHostController,
    signInViewmodel: SignInViewmodel= viewModel()
){
    var name by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
Column(modifier = Modifier
    .fillMaxSize(),
    ) {
Head(modifier = Modifier){navHostController.navigate(SignUp)}
    ImgUser(modifier = Modifier.padding(top = 50.dp),icon =R.drawable.user)
    Textfiel(modifier = Modifier.padding(top = 50.dp), username = name, password = password, Onchangeusername ={name=it} ) {it
        password=it
    }
    SpacerHeight(40.dp)
    ButtonLogin (modifier = Modifier
       ){
        if (name.isNotEmpty()&& password.isNotEmpty()){
signInViewmodel.signIn(password,name,navHostController,context)}
        else{
            Toast.makeText(context, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show()
        }
    }
}
    }
}

@Composable
fun Head(modifier: Modifier=Modifier,onclick: () -> Unit){
    Row (modifier = modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween){
        Text(text = stringResource(R.string.login),
            style = TextStyle(
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        )
//
        Text(text = stringResource(R.string.sign_up),
            style = TextStyle(
                color = Color.Gray,
                fontSize = 20.sp,
            ),
            modifier=Modifier.clickable { onclick ()}
        )

    }
}
@Composable
fun ImgUser(modifier: Modifier=Modifier,
            @DrawableRes icon: Int){
    Box(modifier =modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
){
IconComponentDrawable(icon = icon, modifier = Modifier
    .border(10.dp, Gray400, CircleShape), size =150.dp )
    }
}
@Composable
fun Textfiel(
    modifier: Modifier=Modifier,
    username: String,
    password :String,
    Onchangeusername :(String)->Unit,
    Onchangepassword :(String)->Unit
){
    Box(modifier = modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center){
        Column {

        OutlinedTextField(
            value = username,
            onValueChange = Onchangeusername,
            label = { Text(text = stringResource(R.string.username_or_email_adress)) },
            placeholder = { Text(text = stringResource(R.string.username_or_email_adress)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(

                        unfocusedTextColor = Color .Black,
                focusedTextColor = Color.Black,
                disabledContainerColor = Color.Transparent,
                errorPlaceholderColor = Color.Transparent
            ),
        )
        SpacerHeight(20.dp)
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
fun ButtonLogin(
    modifier: Modifier=Modifier,
    onclick:()->Unit
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(horizontal = 50.dp)
        .border(3.dp, Gray400, RoundedCornerShape(30.dp)) ,

        contentAlignment = Alignment.Center){
    ButtonComponent (modifier = Modifier.fillMaxSize(), text = "Log in"){onclick()
    }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Test2(){
//LoginScreen()
}