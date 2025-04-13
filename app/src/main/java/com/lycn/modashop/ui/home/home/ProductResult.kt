package com.lycn.modashop.ui.home.home

import com.lycn.modashop.ui.home.home.products.ProductView

data class ProductResult(
    val productView: ProductView? = null,
    val error: Int? = null,
) {
}