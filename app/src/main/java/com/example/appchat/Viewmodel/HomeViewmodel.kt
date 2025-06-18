package com.example.appchat.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appchat.Repo.ChatListRepo
import com.example.appchat.Repo.UsersRepo
import com.example.appchat.data.RecentChats
import com.example.appchat.data.Users

class HomeViewmodel:ViewModel() {

//    private var receiver:Users= Users()
private  var usersRepo=UsersRepo()
    val users :LiveData<List<Users>> = usersRepo.getUsers()
private  var chatListRepo= ChatListRepo()
    val chatList :LiveData<List<RecentChats>> = chatListRepo.getAllChatList()


}