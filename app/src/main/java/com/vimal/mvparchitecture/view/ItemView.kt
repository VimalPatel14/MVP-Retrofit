package com.vimal.mvparchitecture.view

import com.vimal.mvparchitecture.model.Category

interface ItemView {
    fun showCategories(items: List<Category>)
    fun showError(message: String)
}