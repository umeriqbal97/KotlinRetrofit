package com.smartherd.globlofly.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface MessageService {

    @GET
    fun getMessage(@Url anotherServer: String): Call<String>

}