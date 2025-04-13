package com.lycn.modashop.ui.home.home

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val rvHighlightKinds = binding.rvHighlightKinds
        val rvProducts = binding.rvContent

        _homeViewModel.fetchKindResult.observe(viewLifecycleOwner) { data ->
            data.findLast { it.select }?.let {
                _homeViewModel.fetchProductsByKind(it.name)
            }
            _kindAdapter?.apply {
                items = data
                notifyDataSetChanged()
            }
        }

        _homeViewModel.fetchProductResult.observe(viewLifecycleOwner) { data ->
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
        }

        _homeViewModel.fetchProductsByKind(kindView.name)
    }

    private fun onClickProductViewItem(productView: ProductView) {
        // TODO:
    }

    override fun onStart() {
        super.onStart()
        _homeViewModel.fetchKind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}