package com.lycn.modashop.ui.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lycn.modashop.data.model.Kind
import com.lycn.modashop.data.model.Product
import com.lycn.modashop.data.model.Result
import com.lycn.modashop.data.repository.ProductRepository
import com.lycn.modashop.ui.home.home.kinds.KindsView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private val _kindData = MutableLiveData<List<KindsView>>()
    val kindData: LiveData<List<KindsView>> = _kindData
    private val _productData = MutableLiveData<List<Product>>()
    val productData: LiveData<List<Product>> = _productData

    fun fetchKind() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = productRepository.fetchKinds()) {
                is Result.Success -> {
                    val kindViewResults = result.data.map {
                        KindsView(it.name)
                    }
                    _kindData.postValue(kindViewResults)
                }
                is Result.Error -> {
                    // TODO:
                }
            }
        }
    }

    fun fetchProductsByKind(kind: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = productRepository.fetchProductsByKind(kind)) {
                is Result.Success -> {
                    _productData.postValue(result.data)
                }
                is Result.Error -> {
                    // TODO:
                }
            }
        }
    }
}