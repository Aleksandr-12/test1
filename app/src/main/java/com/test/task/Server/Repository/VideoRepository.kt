package com.notepad.Notepad.Retrofit.Repository
import com.notepad.Notepad.Retrofit.RetrofitApi
import com.test.task.Server.Data.Video
import retrofit2.await

class VideoRepository(private val retrofitApi: RetrofitApi) {
    suspend fun getVideoList(): MutableList<Video> = retrofitApi.getVideoList("video",1).await()
}