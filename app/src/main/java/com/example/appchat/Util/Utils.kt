package com.example.appchat.Util

import android.util.Log
import androidx.navigation.NavHostController
import com.example.appchat.data.Users
import com.example.appchat.navigation.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date

class Utils {


    companion object {
//        @SuppressLint("StaticFieldLeak")
//    val context = MyApplication.instance.applicationContext

  //  @SuppressLint("StaticFieldLeak")
 // private var firestore = FirebaseFirestore.getInstance()

        private val auth = FirebaseAuth.getInstance()
        private var userid: String = ""
        private var users :Users= Users()
      private var receiver :Users=Users()
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_IMAGE_PICK = 2
        const val MESSAGE_RIGHT = 1
        const val MESSAGE_LEFT = 2
        const val CHANNEL_ID = "com.example.chatmessenger"
//   var isShowBottom :Boolean =false
//
//
//        fun setisShowBottom(isShow :Boolean){
//            isShowBottom=isShow
//        }
//        fun getisShowBottom():Boolean{
//            return isShowBottom
//        }

        fun getUidLoggedIn(): String {

            if (auth.currentUser!=null){


                userid = auth.currentUser!!.uid



            }
            Log.e("loi","14 :$userid")
            return userid
        }
//        fun getCurrentUser(): Users {
//
//           val firestore = FirebaseFirestore.getInstance()
//            //    val context = MyApplication.instance.applicationContext
//
//            firestore.collection("Users").document(getUidLoggedIn()).get()
//                .addOnSuccessListener {
//                users=Users("1132435353","fdd","fhdfdhf","haha")
//                }
//
//            return users
//        }

        fun Signout(navHostController: NavHostController){
            auth.signOut()
            navHostController.navigate(Login)
        }

        fun getTime(): String {


            val formatter = SimpleDateFormat("HH:mm:ss")
            val date: Date = Date(System.currentTimeMillis())
            val stringdate = formatter.format(date)


            return stringdate

        }

//        suspend   fun getFriendUser(friendid :String):Users {
//Log.e("loi", "1.1.1 $friendid")
//            val firestore = FirebaseFirestore.getInstance()
//            firestore.collection("Users").document(friendid)
//                .get().await()
//                .addOnSuccessListener { document ->
//                    if (document.exists()) {
//                        receiver = document.toObject(Users::class.java)!!
//                        Log.e("loi", "1.1.1 $friendid")
//                    } else {
//                        Log.d("Firestore", "Tài liệu không tồn tại")
//                        Log.e("loi", "1.1.3")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.e("loi", "1.1.2 $friendid")
//                    Log.e("Firestore Error", "Lỗi khi lấy dữ liệu người dùng", exception)
//                }
//            Log.e("loi", "1.1.1 $receiver")
//            return receiver
//        }
//    }
suspend fun getCurrentUser(): Users {
        val firestore = FirebaseFirestore.getInstance()
        val documentSnapshot = firestore.collection("Users").document(getUidLoggedIn()).get().await()
        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Users::class.java)!!
        } else {
            throw IllegalStateException("Tài liệu không tồn tại")
        }
    }

    suspend fun getFriendUser(friendid :String): Users {
        val firestore = FirebaseFirestore.getInstance()
        val documentSnapshot = firestore.collection("Users").document(friendid).get().await()
        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Users::class.java)!!
        } else {
            throw IllegalStateException("Tài liệu không tồn tại")
        }
    }

}
}