package com.example.appchat.Viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appchat.Util.Utils
import com.example.appchat.data.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import kotlin.collections.*
class ChatAppViewModel:ViewModel() {


    val firestore = FirebaseFirestore.getInstance()
private var sender:Users=Users()

    // sendMessage

    fun sendMessage(receiver:Users,message: String) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("loi", "1.1")
            GlobalScope.launch(Dispatchers.Main) {
                val sender: Users = Utils.getCurrentUser()
                Log.e("loi", "1.2 $sender")
                //  val context = MyApplication.instance.applicationContext

                val hashMap = hashMapOf<String, Any>(
                    "sender" to sender.userid!!,
                    "receiver" to receiver.userid!!,
                    "message" to message,
                    "time" to Utils.getTime()
                )


                val uniqueId = listOf(sender.userid, receiver.userid).sorted()
                uniqueId.joinToString(separator = "")

                firestore.collection("Messages").document(uniqueId.toString()).collection("chats")
                    .document(Utils.getTime()).set(hashMap).addOnCompleteListener { taskmessage ->


                        val setHashap = hashMapOf<String, Any>(
                            "friendid" to receiver.userid,
                            "time" to Utils.getTime(),
                            "sender" to Utils.getUidLoggedIn(),
                            "message" to message,
                            "friendsimage" to receiver.imageUrl.toString(),
                            "name" to receiver.username.toString(),
                            "person" to "you"
                        )


                        firestore.collection("Conversation${Utils.getUidLoggedIn()}")
                            .document(receiver.userid)
                            .set(setHashap).addOnCompleteListener() {
                                Log.e("loi", "1.3")
                                val setHashap1 = hashMapOf<String, Any>(
                                    "friendid" to Utils.getUidLoggedIn(),
                                    "time" to Utils.getTime(),
                                    "sender" to receiver.userid,
                                    "message" to message,
                                    "friendsimage" to sender.imageUrl.toString(),
                                    "name" to sender.username.toString(),
                                    "person" to sender.username.toString()
                                )
                                Log.e("loi", "1.4")


                                firestore.collection("Conversation${receiver.userid}")
                                    .document(Utils.getUidLoggedIn())
                                    .set(setHashap1)
                            }
                    }
            }


        }



}