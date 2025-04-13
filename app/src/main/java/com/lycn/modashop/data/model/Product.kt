package com.lycn.modashop.data.model

data class Product(
    private val id: String,
    private val name: String,
    private val currency: String,
    private val imageUrl: String,
    private val price: Int,
    private val kind: String
) {

}