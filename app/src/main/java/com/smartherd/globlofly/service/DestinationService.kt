package com.smartherd.globlofly.service

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*

interface DestinationService {
    @GET("destination")
    fun getDestinationList(@Query("country") country:String?):Call<List<Destination>>

    @GET("destination")
    fun getDestinationList(@QueryMap hashMap: HashMap<String,String>):Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id") id:Int):Call<Destination>

    @POST("destination")
    fun setDestination(@Body newDestination: Destination):Call<Destination>
}