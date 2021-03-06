package com.ntikhoa.gahu.presentation.game.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ntikhoa.gahu.R
import com.ntikhoa.gahu.business.domain.model.Author
import com.ntikhoa.gahu.business.domain.model.Game
import com.ntikhoa.gahu.business.domain.model.Platform
import com.ntikhoa.gahu.databinding.ItemExhaustedBinding
import com.ntikhoa.gahu.databinding.ItemGameBinding

class GameAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val VIEW_ITEM = 0
        const val VIEW_EXHAUSTED = 1

        val EXHAUSTED_OBJ = Game(
            "-1",
            "EXHASTED",
            "",
            "",
            "",
            Author(
                "-1",
                "",
                ""
            )
        )
    }

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(
        GameRecyclerChangeCallBack(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    fun submitList(games: List<Game>) {
        val newList = games.toMutableList()
        differ.submitList(newList)
    }

    fun exhausted() {
        val newList = differ.currentList.toMutableList()
        if (newList[newList.size - 1] != EXHAUSTED_OBJ) {
            newList.add(EXHAUSTED_OBJ)
        }
        differ.submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_EXHAUSTED -> {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_exhausted, parent, false)
                val binding = ItemExhaustedBinding.bind(view)
                ExhaustedViewHolder(binding)
            }

            else -> {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_game, parent, false)
                val binding = ItemGameBinding.bind(view)
                GameViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList[position] == EXHAUSTED_OBJ)
            VIEW_EXHAUSTED
        else VIEW_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val game = differ.currentList[position]
        when (holder) {
            is GameViewHolder -> holder.bind(game)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class GameViewHolder(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.apply {
                Glide.with(root)
                    //.setDefaultRequestOptions(requestOptions) //for cache image
                    .load(game.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(ivGameImage)

                tvGameTitle.text = game.title
            }
        }
    }

    internal inner class GameRecyclerChangeCallBack(
        private val adapter: GameAdapter
    ) : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }
    }
}