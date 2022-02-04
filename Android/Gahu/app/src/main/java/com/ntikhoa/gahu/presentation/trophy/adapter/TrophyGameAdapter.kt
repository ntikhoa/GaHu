package com.ntikhoa.gahu.presentation.trophy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ntikhoa.gahu.business.domain.model.TrophyGame
import com.ntikhoa.gahu.databinding.ItemTrophyGameBinding

class TrophyGameAdapter : ListAdapter<TrophyGame, TrophyGameAdapter.TrophyGameViewHolder>(
    DIFF_CALLBACK
) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TrophyGame>() {
            override fun areItemsTheSame(oldItem: TrophyGame, newItem: TrophyGame): Boolean {
                return oldItem.slug == newItem.slug
            }

            override fun areContentsTheSame(oldItem: TrophyGame, newItem: TrophyGame): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrophyGameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrophyGameBinding.inflate(inflater, parent, false)
        return TrophyGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrophyGameViewHolder, position: Int) {
        val trophyGame = currentList[position]
        holder.bind(trophyGame)
    }

    class TrophyGameViewHolder(private val binding: ItemTrophyGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trophyGame: TrophyGame) {
            binding.apply {
                Glide.with(root)
                    //.setDefaultRequestOptions(requestOptions) //for cache image
                    .load(trophyGame.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(itemGame.ivGameImage)

                itemGame.tvGameTitle.text = trophyGame.title

                tvEarned.text = trophyGame.got.toString()
                tvTotal.text = trophyGame.trophy.totalTrophies.toString()

                layoutCompletion.pbCompletion.progress = trophyGame.getCompletion()
                layoutCompletion.tvCompletion.text = trophyGame.getCompletion().toString() + "%"

                tvPlatinum.text = trophyGame.trophy.platinum.toString()
                tvGold.text = trophyGame.trophy.gold.toString()
                tvSilver.text = trophyGame.trophy.silver.toString()
                tvBronze.text = trophyGame.trophy.bronze.toString()
            }
        }
    }
}