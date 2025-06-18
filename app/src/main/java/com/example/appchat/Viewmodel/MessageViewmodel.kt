package com.example.appchat.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appchat.Repo.MessageRepo
import com.example.appchat.data.Messages

class MessageViewmodel:ViewModel( ) {
    private var messageRepo= MessageRepo()
    fun getmessages(friend :String):LiveData<List<Messages>> {
        val messages: LiveData<List<Messages>> = messageRepo.getMessages(friend)
        return messages
    }
}