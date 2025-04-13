package com.lycn.modashop.ui.home.home

import android.icu.text.DecimalFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lycn.modashop.data.model.Product
import com.lycn.modashop.data.model.Result
import com.lycn.modashop.data.repository.ProductRepository
import com.lycn.modashop.ui.home.home.kinds.KindView
import com.lycn.modashop.ui.home.home.products.ProductView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private val _fetchKindResult = MutableLiveData<List<KindView>>()
    val fetchKindResult: LiveData<List<KindView>> = _fetchKindResult
    private val _fetchProductResult = MutableLiveData<List<ProductView>>()
    val fetchProductResult: LiveData<List<ProductView>> = _fetchProductResult

    fun fetchKind() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = productRepository.fetchKinds()) {
                is Result.Success -> {
                    val kindViewResults = result.data.map {
                        KindView(it.name)
                    }
                    _fetchKindResult.postValue(kindViewResults)
                }

                is Result.Error -> {
                    // TODO:
                }
            }
        }
    }

    fun fetchProductsByKind(kind: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = productRepository.fetchProductsByKind(kind)) {
                is Result.Success -> {
                    val productViewResult = result.data.map { product ->
                        product.toProductView()
                    }
                    _fetchProductResult.postValue(productViewResult)
                }

                is Result.Error -> {
                    // TODO:
                }
            }
        }
    }
}

fun Product.toProductView(): ProductView {
    return ProductView(
        id = id,
        name = name,
        price = DecimalFormat.getInstance(Locale.GERMAN).format(price),
        currency = currency,
        imageUrl = imageUrl,
    )
}