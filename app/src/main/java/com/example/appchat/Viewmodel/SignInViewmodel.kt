package com.example.appchat.Viewmodel

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.appchat.navigation.Home
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore

class SignInViewmodel :ViewModel() {

    private var    auth = FirebaseAuth.getInstance()
   private var     firestore = FirebaseFirestore.getInstance()
       lateinit var pd:ProgressDialog

    fun signIn(password: String, email: String,navHostController: NavHostController,context: Context) {
       pd=ProgressDialog(context)
       pd.show()
       pd.setMessage("Signing In")

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {


            if (it.isSuccessful){

               pd.dismiss()
Toast.makeText(context,"Đăng nhập thành công",Toast.LENGTH_SHORT).show()
navHostController.navigate(Home)

            } else {

                pd.dismiss()
               Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()


            }


        }.addOnFailureListener {exception->


            when (exception){

                is FirebaseAuthInvalidCredentialsException ->{

                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()


                }

                else-> {

                    // other exceptions
                   Toast.makeText(context, "Auth Failed", Toast.LENGTH_SHORT).show()


                }



            }





        }


    }
//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        super.onBackPressed()
//
//       // pds.dismiss()
//        finish()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//     //   pds.dismiss()
//
//    }

}