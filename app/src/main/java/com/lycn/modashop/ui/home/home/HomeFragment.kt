package com.lycn.modashop.ui.home.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.lycn.modashop.databinding.FragmentHomeBinding
import com.lycn.modashop.ui.home.home.kinds.KindsAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

/**
 * https://www.gucci.com/us/en/ca/women/ready-to-wear-for-women/dresses-and-jumpsuits-for-women/mini-dresses-for-women-c-women-readytowear-short-dresses
 */

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private var kindsAdapter: KindsAdapter? = null

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


        homeViewModel.kindData.observe(viewLifecycleOwner) { data ->
            kindsAdapter?.items = data
            kindsAdapter?.notifyDataSetChanged()
        }

        kindsAdapter = KindsAdapter(listOf()) { kindView ->
            if (kindView.select) {
                // do nothing
            } else {
                kindView.select = true
                kindsAdapter?.items?.forEach { item ->
                    if (item != kindView) {
                        item.select = false
                    }
                }
                kindsAdapter?.notifyDataSetChanged()
            }

        }
        binding.rvHighlightKinds.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHighlightKinds.adapter = kindsAdapter

        return root
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.fetchKind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}