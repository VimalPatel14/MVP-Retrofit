package com.vimal.mvparchitecture

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.vimal.mvparchitecture.adapter.ItemAdapter
import com.vimal.mvparchitecture.adapter.ShimmerAdapter
import com.vimal.mvparchitecture.databinding.ActivityMainBinding
import com.vimal.mvparchitecture.model.Category
import com.vimal.mvparchitecture.presenter.ItemPresenter
import com.vimal.mvparchitecture.presenter.ItemPresenterImpl
import com.vimal.mvparchitecture.view.ItemView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ItemView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: ItemPresenter
    private lateinit var adapter: ItemAdapter
    lateinit var adapterShimmer: ShimmerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dummyDataList: List<String> = listOf(
            "Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
            "Item 6", "Item 7", "Item 8", "Item 9", "Item 10",
            "Item 11", "Item 12", "Item 13", "Item 14", "Item 15",
        )
        adapterShimmer = ShimmerAdapter(dummyDataList)
        binding.progressDialog.adapter = adapterShimmer
        binding.progressDialog.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = ItemAdapter()
        binding.recyclerView.adapter = adapter

        presenter = ItemPresenterImpl()
        presenter.attachView(this)
        presenter.loadCategories()
    }

    override fun showCategories(items: List<Category>) {
        lifecycleScope.launch {
            delay(500)
            adapter.setItems(items)
            binding.progressDialog.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }

    }

    override fun showError(message: String) {
        // Show error message
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}