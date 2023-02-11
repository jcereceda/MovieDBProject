package com.utad.api_peliculas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.utad.api_peliculas.model.PeliculasResponse
import com.utad.api_peliculas.R
import com.utad.api_peliculas.api.ApiRest

class PeliculasAdapter  (
    private val data: ArrayList<PeliculasResponse.Pelicula>,
    private val onClick: (PeliculasResponse.Pelicula) -> Unit
    )  : RecyclerView.Adapter<PeliculasAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.imgPeli)
        fun bind(pelicula: PeliculasResponse.Pelicula) {
            Picasso.get().load(ApiRest.URL_IMAGES + pelicula.poster_path).into(img)
            itemView.findViewById<TextView>(R.id.tituloPeli).text = pelicula.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_peliculas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        val elemento = holder.itemView.findViewById<CardView>(R.id.card)
        holder.itemView.setOnClickListener{
            onClick(data[position])
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

}