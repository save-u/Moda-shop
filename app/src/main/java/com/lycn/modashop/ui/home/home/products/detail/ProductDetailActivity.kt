package com.lycn.modashop.ui.home.home.products.detail

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.lycn.modashop.R
import com.lycn.modashop.databinding.ActivityProductDetailBinding
import com.lycn.modashop.ui.home.home.HomeFragment
import com.lycn.modashop.ui.home.home.products.ProductView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityProductDetailBinding
    private val detailViewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityProductDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(_binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _binding.btnBack.setOnClickListener {
            finish()
        }

        _binding.btnAddToCart.setOnClickListener {
            //todo
        }

        detailViewModel.productResult.observe(this) { data ->
            bindingProductView(data)
        }
    }

    override fun onStart() {
        super.onStart()
        intent.getStringExtra(HomeFragment.PRODUCT_ID_KEY)?.let {
            detailViewModel.fetchProduct(it)
        }
    }

    private fun bindingProductView(productView: ProductView) {
        _binding.tvName.text = productView.name
        _binding.tvPrice.text = productView.price
        _binding.tvCurrency.text = productView.currency
        Glide.with(this)
            .load(productView.imageUrl)
            .error(R.drawable.ic_baseline_broken_image_50)
            .placeholder(R.drawable.ic_outline_image_50)
            .into(_binding.imgProduct)
    }
}