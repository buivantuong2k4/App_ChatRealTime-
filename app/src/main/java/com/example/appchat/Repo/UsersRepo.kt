package com.example.appchat.Repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appchat.Util.Utils
import com.example.appchat.data.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UsersRepo {
    private val firestore = FirebaseFirestore.getInstance()
private val auth=FirebaseAuth.getInstance()

    fun getUsers(): LiveData<List<Users>> {

        Log.e("loi","5")
        // Tạo một MutableLiveData để có thể cập nhật giá trị sau này
        val users = MutableLiveData<List<Users>>()
        Log.e("loi","6")
        firestore.collection("Users").addSnapshotListener { snapshot, exception ->
            Log.e("loi","7")
            if (exception != null) {
                Log.e("loi","8")
                return@addSnapshotListener
            }

            val usersList = mutableListOf<Users>()
            snapshot?.documents?.forEach { document ->
                Log.e("loi","9 $document")
                val user = document.toObject(Users::class.java)
                Log.e("loi","9,5 : $user")




           if (user!!.userid != Utils.getUidLoggedIn()) {

//                    user.let {
                        Log.e("loi","11")

                user?.let { usersList.add(it) }
                  // }


               // }


                users.value = usersList
                Log.e("loi","12 $users")
            }
            Log.e("loi","13 $users")

        }}

        return users

    }
}