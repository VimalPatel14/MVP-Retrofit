package com.vimal.mvparchitecture.api

import com.vimal.mvparchitecture.model.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("categories.php")
    fun getCategories(): Call<CategoryResponse>
}