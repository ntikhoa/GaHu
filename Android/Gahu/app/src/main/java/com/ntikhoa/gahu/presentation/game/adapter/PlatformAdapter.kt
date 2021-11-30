package com.ntikhoa.gahu.presentation.game.adapter

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.model.Platform
import com.ntikhoa.gahu.databinding.ItemPlatformBinding

class PlatformAdapter : ListAdapter<Platform, PlatformAdapter.PlatformViewHolder>(DIFF_CALLBACK) {

    private lateinit var context: Context

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Platform>() {
            override fun areItemsTheSame(oldItem: Platform, newItem: Platform): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Platform, newItem: Platform): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun submitList(list: MutableList<Platform>?) {
        list?.add(0, Platform.ALL_PLATFORM)
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_platform, parent, false)
        return PlatformViewHolder(ItemPlatformBinding.bind(view))
    }

    override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
        val platform = currentList[position]
        platform?.let {
            holder.bind(it, context)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class PlatformViewHolder(private val binding: ItemPlatformBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(platform: Platform, context: Context) {
            binding.btnPlatform.text = platform.name

            //All platform's id
            if (platform == Platform.ALL_PLATFORM) {
                binding.btnPlatform.backgroundTintList = ContextCompat.getColorStateList(context, R.color.platform_color)
            }
        }
    }
}