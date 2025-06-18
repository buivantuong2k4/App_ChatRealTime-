package com.example.appchat.Repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appchat.Util.Utils
import com.example.appchat.data.RecentChats
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatListRepo{

    val firestore = FirebaseFirestore.getInstance()


    fun getAllChatList(): LiveData<List<RecentChats>> {

        val AllChatList = MutableLiveData<List<RecentChats>>()


        // SHOWING THE RECENT MESSAGED PERSON ON TOP
        firestore.collection("Conversation${Utils.getUidLoggedIn()}").orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->


                if (exception != null) {

                    return@addSnapshotListener
                }


                val chatlist = mutableListOf<RecentChats>()

                snapshot?.forEach { document ->

                    val chatlistmodel = document.toObject(RecentChats::class.java)


                    if (chatlistmodel.sender.equals(Utils.getUidLoggedIn())) {


                        chatlistmodel.let {


                            chatlist.add(it)

                        }


                    }

                }


                AllChatList.value = chatlist


            }

        return AllChatList


    }
}