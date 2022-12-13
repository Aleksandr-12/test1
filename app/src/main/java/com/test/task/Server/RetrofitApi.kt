package com.notepad.Notepad.Retrofit

import com.test.task.Server.Data.Video
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApi {
    @GET("backgrounds")
    fun getVideoList(@Query("group") group:String,
                     @Query("category_id") category_id:Int): Deferred<MutableList<Video>>

}