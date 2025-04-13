package com.lycn.modashop.ui.home.home.kinds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.lycn.modashop.R
import com.lycn.modashop.databinding.ItemKindBinding

class KindsAdapter(
    var items: List<KindsView>,
    private val onItemClick: (KindsView) -> Unit = {}
) :
    RecyclerView.Adapter<KindsAdapter.KindViewHolder>() {

    inner class KindViewHolder(private val binding: ItemKindBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(kindView: KindsView) {
            binding.tvKindName.text = kindView.name
            if (kindView.select) {
                binding.imgKind.setBackgroundResource(R.drawable.bg_selected)
            } else {
                binding.imgKind.setBackgroundResource(R.drawable.bg_unselected)
            }
            binding.root.setOnClickListener {
                onItemClick(kindView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KindViewHolder {
        val binding = ItemKindBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KindViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: KindViewHolder, position: Int) {
        holder.binding(items[position])
    }
}