package com.emanh.mixivivu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.emanh.mixivivu.activity.DetailShipActivity
import com.emanh.mixivivu.databinding.ViewholderShipBinding
import com.emanh.mixivivu.model.ShipModel

class ShipAdapter(private val items: MutableList<ShipModel>)
    : RecyclerView.Adapter<ShipAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder (val binding: ViewholderShipBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderShipBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val countReview = items[position].evaluate.size
        var sumRating = 0
        for (index in 0..<countReview) {
            sumRating += items[position].evaluate[index].rating
        }

        val rating = if (countReview > 0) sumRating.toFloat() / countReview else 0.0f
        val formattedRating = String.format("%.1f", rating)

        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.picUrl)

        holder.binding.title.text = "Du thuyền ${items[position].title}"
        holder.binding.location.text = items[position].location
        holder.binding.price.text = "${items[position].price}đ / khách"
        holder.binding.textEvaluate.text = "$formattedRating (${countReview} đánh giá)"

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailShipActivity::class.java)
            intent.putExtra("objectShip", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}