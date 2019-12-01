package com.lfkekpoint.blockchain.task.presentation.base.list.mvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.lfkekpoint.blockchain.task.presentation.base.list.IBaseListAdapter

class VmListAdapter : RecyclerView.Adapter<VmViewHolder>(), IBaseListAdapter<IBaseItemVm> {

    var items = ArrayList<IBaseItemVm>()

    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int) = items[position].layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VmViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater!!, viewType, parent, false)

        return VmViewHolder(viewDataBinding)

    }

    override fun onBindViewHolder(holder: VmViewHolder, position: Int) {

        holder.binding.setVariable(items[position].brVariableId, items[position])
        holder.binding.executePendingBindings()
    }

    override fun add(newItem: IBaseItemVm) {

        items.add(newItem)
        notifyItemInserted(items.lastIndex)
    }

    override fun add(newItems: ArrayList<IBaseItemVm>) {

        val oldSize = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(oldSize, newItems.size)
    }

    override fun clearAll() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {

        val pos = items.size - position
        return super.getItemId(pos)
    }

    override fun addAtPosition(pos: Int, newItem: IBaseItemVm) {

        items.add(pos, newItem)
        notifyItemInserted(pos)
    }

    override fun remove(position: Int) {

        items.removeAt(position)
        notifyItemRemoved(position)
    }
}