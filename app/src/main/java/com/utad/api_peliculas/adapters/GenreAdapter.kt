package com.utad.api_peliculas.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.utad.api_peliculas.R
import com.utad.api_peliculas.model.Genre
import org.xmlpull.v1.XmlPullParser

class GenreAdapter  (private val data: ArrayList<Genre>,
                     val layoutAPoner: Int,
                     val onClick: (Genre) -> Unit
                    ) : RecyclerView.Adapter<GenreAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_item, parent, false)
        val view = LayoutInflater.from(parent.context).inflate(layoutAPoner, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

        val elemento = holder.itemView.findViewById<CardView>(R.id.card)
        elemento.setOnClickListener {
            onClick(data[position])
        }

    }

    override fun getItemCount(): Int = data.size
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genero  = itemView.findViewById<TextView>(R.id.genero)
        val card  = itemView.findViewById<CardView>(R.id.card)
        fun bind(item: Genre) {
            genero.text = item.name

            card.setCardBackgroundColor(generateColor().toInt())
            card.setOnClickListener {
                Log.v("Pulso sobre", item.id.toString())
            }
        }
        fun generateColor(): Long {
            val colors = arrayListOf(0xff14d3ec, 0xFF58A2AC,0xff3043A3,0xff1A2765,
                0xff0BA5B3,0xff5F0BB3)
            return colors.random()
        }
    }
}