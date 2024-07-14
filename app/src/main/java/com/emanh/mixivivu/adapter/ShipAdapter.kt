package com.emanh.mixivivu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.emanh.mixivivu.databinding.ViewholderShipBinding
import com.emanh.mixivivu.model.ShipModel

class ShipAdapter(private val items: MutableList<ShipModel>)
    : RecyclerView.Adapter<ShipAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder (val binding: ViewholderShipBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderShipBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShipAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.picUrl)

        holder.binding.title.text = "Du thuyền " + items[position].title
        holder.binding.location.text = items[position].location
        holder.binding.price.text = items[position].price.toString() + "đ / khách"
        holder.binding.textEvaluate.text = items[position].evaluate.calculateAverageRating().toString() + " (${items[position].evaluate.countReview()}) " + "đánh giá"
    }

    override fun getItemCount(): Int = items.size
}