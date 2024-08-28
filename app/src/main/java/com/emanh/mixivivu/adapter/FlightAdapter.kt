package com.emanh.mixivivu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emanh.mixivivu.databinding.ViewholderFlightBinding
import com.emanh.mixivivu.model.PlaneModel
import java.text.NumberFormat
import java.util.Locale

class FlightAdapter(private val items: MutableList<PlaneModel>)
    : RecyclerView.Adapter<FlightAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ViewholderFlightBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderFlightBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FlightAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(items[position].picLogo)
            .into(holder.binding.picAirline)

        holder.binding.nameAirline.text = items[position].airline
        holder.binding.namePlane.text = items[position].namePlane
        holder.binding.airport.text = "${items[position].startingPoint} - ${items[position].destination}"
        holder.binding.timeline.text = "${items[position].takeOffTime} - ${items[position].landingTime}"
        holder.binding.price.text = "${formatPrice(items[position].ticketPrice)} VNĐ"
    }

    override fun getItemCount(): Int = items.size

    private fun formatPrice(price: Int): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        return numberFormat.format(price)
    }
}