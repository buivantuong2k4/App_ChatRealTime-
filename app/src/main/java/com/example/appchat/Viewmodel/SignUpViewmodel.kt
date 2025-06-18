package com.example.appchat.Viewmodel

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.appchat.Util.StorageUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class SignUpViewmodel:ViewModel(){


        private  var auth =FirebaseAuth.getInstance()
        private  var firestore = FirebaseFirestore.getInstance()
      lateinit var pd: ProgressDialog


   fun createAnAccount(uri: Uri?,name: String, password: String, email: String,context: Context) {
       Log.e("loi","3")
       pd=ProgressDialog(context)
       pd.show()
       Log.e("loi","3")
       pd.setMessage("Registering User")
       Log.e("loi","3")

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task->

            if (task.isSuccessful){
                Log.e("loi","4")
                val user = auth.currentUser
                val     dataHashMap = hashMapOf(
                    "userid" to user!!.uid!!,
                    "username" to name,
                    "useremail" to email,
                    "status" to "default",
                    "imageUrl" to "https://www.pngarts.com/files/6/User-Avatar-in-Suit-PNG.png"
                )
                if (uri!=null) {

                    val unique_image_name = UUID.randomUUID()
                    StorageUtil.uploadToStorage(uri, context, "image", unique_image_name.toString())
                    dataHashMap["imageUrl"]=unique_image_name.toString()
                }


                firestore.collection("Users").document(user.uid).set(dataHashMap)

                pd.dismiss()
                Toast.makeText(context, "Đăng kí  thành công", Toast.LENGTH_SHORT).show()

            }
            else {
                pd.dismiss()
                Toast.makeText(context, "Lỗi đăng kí", Toast.LENGTH_SHORT).show()
            }

        }




    }
}