package com.notepad.Notepad.Retrofit.Common

import com.notepad.Notepad.Retrofit.RetrofitApi
import com.notepad.Notepad.Retrofit.RetrofitClient

object  Common {
    private const val BASE_URL = "https://dev.bgrem.deelvin.com/api/"
    val retrofitApi: RetrofitApi
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitApi::class.java)
}