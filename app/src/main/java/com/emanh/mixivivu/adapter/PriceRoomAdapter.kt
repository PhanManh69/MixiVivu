package com.emanh.mixivivu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emanh.mixivivu.databinding.ViewholderPriceRoomBinding
import com.emanh.mixivivu.model.TypeRoomModel

class PriceRoomAdapter(private val items: MutableList<TypeRoomModel>)
    : RecyclerView.Adapter<PriceRoomAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ViewholderPriceRoomBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderPriceRoomBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text = "Phòng ${items[position].name}"
        holder.binding.area.text = "${items[position].area} m²"
        holder.binding.capacity.text = "Tối đa: ${items[position].capacity}"
        holder.binding.price.text = "${items[position].price}đ / Khách"

        Glide.with(holder.itemView.context)
            .load(items[position].banner)
            .into(holder.binding.picUrl)
    }

    override fun getItemCount(): Int = items.size
}