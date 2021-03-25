//package com.example.kotlingithubuser
//
//import java.io.FileReader
//import com.google.gson.Gson
//
//object GithubUserData {
//    private val gson = Gson()
//    private val githubUserData = gson.fromJson(FileReader("@raw/githubuser.json"), GithubUser::class.java)
//
//    val listData: ArrayList<GithubUser>
//        get() {
//            val list = arrayListOf<GithubUser>()
//
//            for (index in githubUserData.users.indices) {
//
//            }
//
//            return list
//        }
//}