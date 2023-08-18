package com.vimal.mvparchitecture.presenter

import com.vimal.mvparchitecture.view.ItemView

interface ItemPresenter {
    fun attachView(view: ItemView)
    fun detachView()
    fun loadCategories()
}