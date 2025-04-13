package com.lycn.modashop.ui.home.home.products.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lycn.modashop.data.model.Result
import com.lycn.modashop.data.repository.ProductRepository
import com.lycn.modashop.ui.home.home.products.ProductView
import com.lycn.modashop.ui.home.home.toProductView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private val _productResult = MutableLiveData<ProductView>()
    val productResult: LiveData<ProductView> = _productResult

    fun fetchProduct(productId: String) {
        viewModelScope.launch {
            when (val result = productRepository.fetchProductById(productId)) {
                is Result.Success -> {
                    _productResult.postValue(result.data.toProductView())
                }

                is Result.Error -> {
                    // TODO:
                }
            }
        }
    }
}