package com.lycn.modashop.ui.home.home.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lycn.modashop.R
import com.lycn.modashop.databinding.ItemProductBinding

class ProductAdapter(
    var items: List<ProductView>,
    private val onItemClick: (ProductView) -> Unit = {}
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(productView: ProductView) {
            binding.tvName.text = productView.name
            binding.tvPrice.text = productView.price
            binding.tvCurrency.text = productView.currency

            Glide.with(binding.root)
                .load(productView.imageUrl)
                .error(R.drawable.ic_baseline_broken_image_50)
                .placeholder(R.drawable.ic_outline_image_50)
                .into(binding.imgProduct)

            binding.root.setOnClickListener {
                onItemClick(productView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding(items[position])
    }
}