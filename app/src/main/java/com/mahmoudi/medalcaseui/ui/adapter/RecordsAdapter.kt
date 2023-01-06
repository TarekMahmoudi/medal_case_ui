package com.mahmoudi.medalcaseui.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudi.medalcaseui.R
import com.mahmoudi.medalcaseui.databinding.ItemHeaderBinding
import com.mahmoudi.medalcaseui.databinding.ItemMedalBinding
import com.mahmoudi.medalcaseui.model.Header
import com.mahmoudi.medalcaseui.model.Medal
import com.mahmoudi.medalcaseui.model.RecordCollection


private val listDiffer = object : DiffUtil.ItemCallback<RecordCollection>() {
    override fun areItemsTheSame(oldItem: RecordCollection, newItem: RecordCollection): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RecordCollection, newItem: RecordCollection): Boolean =
        oldItem == newItem
}

class RecordsAdapter :
    ListAdapter<RecordCollection, BaseViewHolder<*>>(listDiffer) {

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_MEDAL = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflator = LayoutInflater.from(parent.context)
        return when (viewType) {

            ITEM_VIEW_TYPE_HEADER -> {
                val binding =
                    DataBindingUtil.inflate<ItemHeaderBinding>(
                        layoutInflator,
                        R.layout.item_header,
                        parent,
                        false
                    )
                HeaderViewHolder(binding)
            }

            ITEM_VIEW_TYPE_MEDAL -> {
                val binding =
                    DataBindingUtil.inflate<ItemMedalBinding>(
                        layoutInflator,
                        R.layout.item_medal,
                        parent,
                        false
                    )
                MedalViewHolder(binding, parent.context)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(item as Header)
            is MedalViewHolder -> holder.bind(item as Medal)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Header -> ITEM_VIEW_TYPE_HEADER
            is Medal -> ITEM_VIEW_TYPE_MEDAL
        }
    }

}

internal class HeaderViewHolder(private val binding: ItemHeaderBinding) :
    BaseViewHolder<Header>(binding.root) {
    override fun bind(item: Header) {
        binding.recordTypeText.text = item.name
        binding.recordCount.text = "${item.completedCount} of " + item.totalCount
    }
}

internal class MedalViewHolder(val binding: ItemMedalBinding, val context: Context) :
    BaseViewHolder<Medal>(binding.root) {
    override fun bind(item: Medal) {
        binding.recordTitleText.text = item.name
        binding.recordTimeText.text = item.recordValue
        val isVisible = when (item.isCompleted) {
            true -> View.INVISIBLE
            false -> View.VISIBLE
        }
        binding.recordDisabled.visibility = isVisible
        binding.recordImage.setImageDrawable(context.getDrawable(item.imageUrl))
    }

}

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)
}