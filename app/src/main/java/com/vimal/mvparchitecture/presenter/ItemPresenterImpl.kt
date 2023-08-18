package com.vimal.mvparchitecture.presenter

import android.content.ClipData
import com.vimal.mvparchitecture.api.ApiService
import com.vimal.mvparchitecture.model.CategoryResponse
import com.vimal.mvparchitecture.view.ItemView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemPresenterImpl : ItemPresenter {

    private var view: ItemView? = null

    override fun attachView(view: ItemView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun loadCategories() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        val call = api.getCategories()

        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful) {
                    val categoryResponse = response.body()
                    categoryResponse?.let {
                        val categories = categoryResponse.categories
                        view?.showCategories(categories)
                    }
                } else {
                    view?.showError("Error loading items")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                view?.showError("Network error")
            }
        })
    }
}
