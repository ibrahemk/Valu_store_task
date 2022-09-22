package com.example.valu_store.Retrofitapis


import retrofit2.Call
import retrofit2.http.*



interface APIInterface {

    @GET
    fun Getrequest(@Url url:String,): Call<String?>?




}