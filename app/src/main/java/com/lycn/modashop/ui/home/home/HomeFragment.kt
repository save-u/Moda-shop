package com.lycn.modashop.ui.home.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lycn.modashop.databinding.FragmentHomeBinding
import com.lycn.modashop.ui.home.home.kinds.KindAdapter
import com.lycn.modashop.ui.home.home.kinds.KindView
import com.lycn.modashop.ui.home.home.products.ProductAdapter
import com.lycn.modashop.ui.home.home.products.ProductView
import com.lycn.modashop.ui.home.home.products.detail.ProductDetailActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * https://www.gucci.com/us/en/ca/women/ready-to-wear-for-women/dresses-and-jumpsuits-for-women/mini-dresses-for-women-c-women-readytowear-short-dresses
 */

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val _homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private var _kindAdapter: KindAdapter? = null
    private var _productAdapter: ProductAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        val PRODUCT_ID_KEY = HomeFragment::class.java.name + "PRODUCT_ID_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val rvHighlightKinds = binding.rvHighlightKinds
        val rvProducts = binding.rvContent
        val pbProductLoading = binding.pbProductLoading

        _homeViewModel.fetchKindResult.observe(viewLifecycleOwner) { data ->
            data.findLast { it.select }?.let {
                fetchProductByKind(it.name)
            }
            _kindAdapter?.apply {
                items = data
                notifyDataSetChanged()
            }
        }

        _homeViewModel.fetchProductResult.observe(viewLifecycleOwner) { data ->
            pbProductLoading.visibility = View.GONE
            _productAdapter?.apply {
                items = data
                notifyDataSetChanged()
            }
        }

        // Init Highlight kinds data
        rvHighlightKinds.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        _kindAdapter = KindAdapter(listOf(), ::onClickKindViewItem)
        rvHighlightKinds.adapter = _kindAdapter

        // Init Content kind selected
        _productAdapter = ProductAdapter(listOf(), ::onClickProductViewItem)
        rvProducts.layoutManager = GridLayoutManager(context, 2)
        rvProducts.adapter = _productAdapter

        return root
    }

    private fun onClickKindViewItem(kindView: KindView) {
        if (kindView.select) {
            // do nothing
        } else {
            kindView.select = true
            _kindAdapter?.items?.forEach { item ->
                if (item != kindView) {
                    item.select = false
                }
            }
            _kindAdapter?.notifyDataSetChanged()

            fetchProductByKind(kindView.name)
        }
    }

    private fun fetchKinds() {
        _homeViewModel.fetchKind()
    }

    private fun fetchProductByKind(kind: String) {
        _binding?.pbProductLoading?.visibility = View.VISIBLE
        _homeViewModel.fetchProductsByKind(kind)
    }

    private fun onClickProductViewItem(productView: ProductView) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
        intent.putExtra(PRODUCT_ID_KEY, productView.id)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        fetchKinds()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}