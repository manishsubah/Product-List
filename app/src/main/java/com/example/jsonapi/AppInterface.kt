package com.example.jsonapi

import retrofit2.Call
import retrofit2.http.GET

interface AppInterface {
    @GET("products")
    fun getProductData() : Call<MyData>
}