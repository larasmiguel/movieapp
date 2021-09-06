package com.miguel_lara.moviedemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miguel_lara.moviedemo.R
import com.miguel_lara.moviedemo.interfaces.TrailerEvents
import com.miguel_lara.moviedemo.objects.Trailer

class TrailersAdapter(private val trailers: MutableList<Trailer>, private val events: TrailerEvents) :
    RecyclerView.Adapter<TrailersAdapter.CustomViewHolder>() {

    fun setData(data: MutableList<Trailer>) {
        trailers.clear()
        trailers.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.trailer_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = trailers.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(trailers[position])
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(trailer: Trailer) {
            val trailerLink = itemView.findViewById<TextView>(R.id.linkTrailer)
            trailerLink.text = trailer.name
            trailerLink.setOnClickListener {
                events.onTrailerClick(trailer)
            }
        }
    }
}
