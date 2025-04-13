package com.lycn.modashop.ui.home.home.kinds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.lycn.modashop.R
import com.lycn.modashop.databinding.ItemKindBinding

class KindAdapter(
    var items: List<KindView>,
    private val onItemClick: (KindView) -> Unit = {}
) :
    RecyclerView.Adapter<KindAdapter.KindViewHolder>() {

    inner class KindViewHolder(private val binding: ItemKindBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(kindView: KindView) {
            binding.tvKindName.text = kindView.name
            @DrawableRes var imgRes: Int = R.drawable.ic_outline_image_not_supported_24
            @DrawableRes var bgRes: Int = R.drawable.bg_unselected
            if (kindView.select) {
                bgRes = R.drawable.bg_selected
                when (kindView.name) {
                    "Popular" -> {
                        imgRes = R.drawable.ic_star_white
                    }

                    "Men" -> {
                        imgRes = R.drawable.ic_men_white
                    }

                    "Woman" -> {
                        imgRes = R.drawable.ic_woman_white
                    }

                    "Kids" -> {
                        imgRes = R.drawable.ic_kid_white
                    }
                }
            } else {
                when (kindView.name) {
                    "Popular" -> {
                        imgRes = R.drawable.ic_star_black
                    }

                    "Men" -> {
                        imgRes = R.drawable.ic_men_black
                    }

                    "Woman" -> {
                        imgRes = R.drawable.ic_woman_black
                    }

                    "Kids" -> {
                        imgRes = R.drawable.ic_kid_black
                    }
                }
                bgRes = R.drawable.bg_unselected
            }
            binding.imgKind.setBackgroundResource(bgRes)
            binding.imgKind.setImageResource(imgRes)
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